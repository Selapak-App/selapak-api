package com.selapak.selapakapi.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.entity.*;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selapak.selapakapi.constant.Payment;
import com.selapak.selapakapi.constant.SurveyStatus;
import com.selapak.selapakapi.constant.TrxStatus;
import com.selapak.selapakapi.constant.Verify;
import com.selapak.selapakapi.exception.TransactionNotFoundException;
import com.selapak.selapakapi.model.request.TransactionRequest;
import com.selapak.selapakapi.model.request.TransactionVerifyRequest;
import com.selapak.selapakapi.repository.TransactionRepository;
import com.selapak.selapakapi.service.AdminService;
import com.selapak.selapakapi.service.BusinessService;
import com.selapak.selapakapi.service.BusinessTypeService;
import com.selapak.selapakapi.service.CustomerService;
import com.selapak.selapakapi.service.LandPriceService;
import com.selapak.selapakapi.service.LandService;
import com.selapak.selapakapi.service.RentPeriodService;
import com.selapak.selapakapi.service.TransactionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AdminService adminService;
    private final CustomerService customerService;
    private final RentPeriodService rentPeriodService;
    private final LandPriceService landPriceService;
    private final BusinessTypeService businessTypeService;
    private final BusinessService businessService;
    private final LandService landService;

    @Override
    public Transaction getById(String id) {
        return transactionRepository.findById(id).orElseThrow(() -> new ApplicationException("Data transaction not found", "Transaction tidak ditemukan", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse create(TransactionRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        RentPeriod rentPeriod = rentPeriodService.getById(request.getRentPeriodId());
        LandPrice landPrice = landPriceService.getById(request.getLandPriceId());
        BusinessType businessType = businessTypeService.getById(request.getBusinessType());

        String landId = landPrice.getLand().getId();
        int availableSlots = landService.getAvailableSlots(landId);

        if(availableSlots < request.getQuantity() || availableSlots <= 0){
            throw new ApplicationException("Data bad request", "Transaksi tidak dapat dilakukan", HttpStatus.BAD_REQUEST);
        }

        Business business = Business.builder()
                .businessName(request.getBusinessName())
                .description(request.getBusinessDescription())
                .businessType(businessType)
                .customer(customer)
                .isActive(true)
                .build();
        businessService.create(business);

        Transaction transaction = Transaction.builder()
                .createdAt(Instant.now().toEpochMilli())
                .updatedAt(Instant.now().toEpochMilli())
                .isActive(true)
                .quantity(request.getQuantity())
                .isSurveyed(false)
                .surveyStatus(SurveyStatus.PENDING)
                .verifyStatus(Verify.PENDING)
                .paymentStatus(Payment.UNPAID)
                .transactionStatus(TrxStatus.ON_PROGRESS)
                .customer(customer)
                .rentPeriod(rentPeriod)
                .landPrice(landPrice)
                .business(business)
                .build();
        transactionRepository.saveAndFlush(transaction);

        landService.decreaseLandSlotAvailable(landId, request.getQuantity());

        return convertToTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse getByIdWithDto(String id) {
        Transaction transaction = getById(id);

        return convertToTransactionResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> getAllWithDto(Integer page, Integer size) {
        Page<Transaction> transactions = transactionRepository.findAll(PageRequest.of(page, size));

        List<TransactionResponse> transactionResponses = transactions.getContent().stream()
                .map(this::convertToTransactionResponse)
                .sorted(Comparator.comparing(TransactionResponse::getUpdatedAt).reversed())
                .toList();

        return new PageImpl<>(transactionResponses, transactions.getPageable(), transactions.getTotalElements());
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionResponse> getAllByCustomerId(String customerId) {
        List<Transaction> transactions = getAll();
        return transactions.stream()
                .filter(transaction -> transaction.getCustomer().getId().equals(customerId))
                .sorted(Comparator.comparing(Transaction::getUpdatedAt).reversed())
                .map(this::convertToTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        Transaction transaction = getById(id);
        transaction.setIsActive(false);
        transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public TransactionResponse verifyApproveTransaction(String id, TransactionVerifyRequest request) {
        Transaction transaction = getById(id);
        Admin admin = adminService.getById(request.getAdminId());

        transaction.setVerifyStatus(Verify.APPROVED);
        transaction.setVerifiedBy(admin);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);

        return convertToTransactionResponse(transaction);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse verifyRejectTransaction(String id, TransactionVerifyRequest request) {
        Transaction transaction = getById(id);
        Admin admin = adminService.getById(request.getAdminId());

        Land land = transaction.getLandPrice().getLand();
        int buyQuantity = transaction.getQuantity();
        int currentSlot = land.getSlotAvailable();
        land.setSlotAvailable(currentSlot + buyQuantity);
        landService.updateSlot(land);

        transaction.setVerifyStatus(Verify.REJECTED);
        transaction.setVerifiedBy(admin);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transaction.setTransactionStatus(TrxStatus.FAILED);
        transactionRepository.saveAndFlush(transaction);

        return convertToTransactionResponse(transaction);
    }

    @Override
    public void doneSurveyLandTransaction(String id) {
        Transaction transaction = getById(id);

        if(transaction.getVerifyStatus().equals(Verify.PENDING) || transaction.getVerifyStatus().equals(Verify.REJECTED)){
            throw new ApplicationException("Data done survey request conflict", "Status verify harus approve dari admin", HttpStatus.CONFLICT);
        }

        transaction.setIsSurveyed(true);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public TransactionResponse acceptTransactionAfterSurveyByCustomer(String id) {
        Transaction transaction = getById(id);

        if(!transaction.getIsSurveyed()){
            throw new ApplicationException("Data accept transaction request conflict", "Customer harus survey terlebih dahulu", HttpStatus.CONFLICT);
        }

        transaction.setSurveyStatus(SurveyStatus.ACCEPTED);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);

        return convertToTransactionResponse(transaction);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse declineTransactionAfterSurveyByCustomer(String id) {
        Transaction transaction = getById(id);

        if(!transaction.getIsSurveyed()){
            throw new ApplicationException("Data decline transaction request conflict", "Customer harus survey terlebih dahulu", HttpStatus.CONFLICT);
        }

        Land land = transaction.getLandPrice().getLand();
        int buyQuantity = transaction.getQuantity();
        int currentSlot = land.getSlotAvailable();
        land.setSlotAvailable(currentSlot + buyQuantity);
        landService.updateSlot(land);

        transaction.setSurveyStatus(SurveyStatus.DECLINED);
        transaction.setTransactionStatus(TrxStatus.FAILED);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);

        return convertToTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse payTransaction(String id) {
        Transaction transaction = getById(id);

        if(transaction.getSurveyStatus().equals(SurveyStatus.DECLINED)){
            throw new ApplicationException("Data payment request conflict", "Customer harus menyetujui untuk melanjutkan transaksi", HttpStatus.CONFLICT);
        }

        if(transaction.getPaymentStatus().equals(Payment.UNPAID)){
            Instant currentTime = Instant.now();
            Instant oneDayAgo = currentTime.minus(Duration.ofSeconds(86400));
            Instant transactionTime = Instant.ofEpochMilli(transaction.getUpdatedAt());

            if(transactionTime.isBefore(oneDayAgo)){
                transaction.setTransactionStatus(TrxStatus.FAILED);
            }else{
                transaction.setPaymentStatus(Payment.PAID);
                transaction.setTransactionStatus(TrxStatus.DONE);
            }
        }

        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);

        return convertToTransactionResponse(transaction);
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        AdminResponse adminResponse = null;
        if (transaction.getVerifiedBy() != null) {
            Admin admin = adminService.getById(transaction.getVerifiedBy().getId());
            adminResponse = AdminResponse.builder()
                    .id(admin.getId())
                    .name(admin.getName())
                    .email(admin.getEmail())
                    .isActive(admin.getIsActive())
                    .build();
        } 

        Customer customer = customerService.getById(transaction.getCustomer().getId());
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .gender(customer.getGender().toString())
                .address(customer.getAddress())
                .nik(customer.getNik())
                .isActive(customer.getIsActive())
                .updatedAt(customer.getUpdatedAt())
                .build();

        RentPeriod rentPeriod = rentPeriodService.getById(transaction.getRentPeriod().getId());
        RentPeriodResponse rentPeriodResponse = RentPeriodResponse.builder()
                .id(rentPeriod.getId())
                .period(rentPeriod.getPeriod())
                .build();

        LandPrice landPrice = landPriceService.getById(transaction.getLandPrice().getId());
        LandOwnerResponse landOwnerResponse = LandOwnerResponse.builder()
                .id(landPrice.getLand().getLandOwner().getId())
                .name(landPrice.getLand().getLandOwner().getName())
                .email(landPrice.getLand().getLandOwner().getEmail())
                .phoneNumber(landPrice.getLand().getLandOwner().getPhoneNumber())
                .nik(landPrice.getLand().getLandOwner().getNik())
                .isActive(landPrice.getLand().getLandOwner().getIsActive())
                .build();
        LandLessResponse land = LandLessResponse.builder()
                .id(landPrice.getLand().getId())
                .landOwner(landOwnerResponse)
                .address(landPrice.getLand().getAddress())
                .district(landPrice.getLand().getDistrict())
                .village(landPrice.getLand().getVillage())
                .postalCode(landPrice.getLand().getPostalCode())
                .description(landPrice.getLand().getDescription())
                .slotArea(landPrice.getLand().getSlotArea())
                .slotAvailable(landPrice.getLand().getSlotAvailable())
                .totalSlot(landPrice.getLand().getTotalSlot())
                .isActive(landPrice.getLand().getIsActive())
                .landPhotos(landPrice.getLand().getLandPhotos().stream()
                        .map(landPhoto -> LandPhotoResponse.builder()
                                .id(landPhoto.getId())
                                .imageURL(landPhoto.getImageURL())
                                .isActive(landPhoto.getIsActive())
                                .build()).toList())
                .build();
        LandPriceResponse landPriceResponse = LandPriceResponse.builder()
                .id(landPrice.getId())
                .price(landPrice.getPrice())
                .land(land)
                .isActive(landPrice.getIsActive())
                .build();

        Business business = businessService.getById(transaction.getBusiness().getId());
        BusinessResponse businessResponse = BusinessResponse.builder()
                .id(business.getId())
                .businessName(business.getBusinessName())
                .descripttion(business.getDescription())
                .businessType(business.getBusinessType().getName())
                .isActive(business.getIsActive())
                .build();

        Long payment = transaction.getQuantity() * transaction.getLandPrice().getPrice();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .quantity(transaction.getQuantity())
                .totalPayment(payment)
                .verifyBy(adminResponse)
                .verifyStatus(transaction.getVerifyStatus().toString())
                .isSurveyed(transaction.getIsSurveyed())
                .surveyStatus(transaction.getSurveyStatus().toString())
                .paymentStatus(transaction.getPaymentStatus().toString())
                .transactionStatus(transaction.getTransactionStatus().toString())
                .customer(customerResponse)
                .rentPeriod(rentPeriodResponse)
                .landPrice(landPriceResponse)
                .business(businessResponse)
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .isActive(transaction.getIsActive())
                .build();
    }
}
