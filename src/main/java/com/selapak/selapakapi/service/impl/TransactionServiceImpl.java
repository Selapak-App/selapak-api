package com.selapak.selapakapi.service.impl;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.selapak.selapakapi.constant.Payment;
import com.selapak.selapakapi.constant.SurveyStatus;
import com.selapak.selapakapi.constant.TrxStatus;
import com.selapak.selapakapi.constant.Verify;
import com.selapak.selapakapi.exception.TransactionNotFoundException;
import com.selapak.selapakapi.model.entity.Admin;
import com.selapak.selapakapi.model.entity.Business;
import com.selapak.selapakapi.model.entity.BusinessType;
import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.entity.RentPeriod;
import com.selapak.selapakapi.model.entity.Transaction;
import com.selapak.selapakapi.model.request.TransactionRequest;
import com.selapak.selapakapi.model.request.TransactionVerifyRequest;
import com.selapak.selapakapi.model.response.AdminResponse;
import com.selapak.selapakapi.model.response.BusinessResponse;
import com.selapak.selapakapi.model.response.CustomerResponse;
import com.selapak.selapakapi.model.response.LandLessResponse;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.model.response.LandPriceResponse;
import com.selapak.selapakapi.model.response.RentPeriodResponse;
import com.selapak.selapakapi.model.response.TransactionResponse;
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
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse create(TransactionRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        RentPeriod rentPeriod = rentPeriodService.getById(request.getRentPeriodId());
        LandPrice landPrice = landPriceService.getById(request.getLandPriceId());
        BusinessType businessType = businessTypeService.getById(request.getBusinessType());

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

        String landId = landPrice.getLand().getId();
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

        return transactions.map(this::convertToTransactionResponse);
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
    public TransactionResponse verifyRejectTransaction(String id, TransactionVerifyRequest request) {
        Transaction transaction = getById(id);
        Admin admin = adminService.getById(request.getAdminId());

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
        transaction.setIsSurveyed(true);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public TransactionResponse acceptTransactionAfterSurveyByCustomer(String id) {
        Transaction transaction = getById(id);
        transaction.setSurveyStatus(SurveyStatus.ACCEPTED);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);

        return convertToTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse declineTransactionAfterSurveyByCustomer(String id) {
        Transaction transaction = getById(id);
        transaction.setSurveyStatus(SurveyStatus.DECLINED);
        transaction.setTransactionStatus(TrxStatus.FAILED);
        transaction.setUpdatedAt(Instant.now().toEpochMilli());
        transactionRepository.saveAndFlush(transaction);

        return convertToTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse payTransaction(String id) {
        Transaction transaction = getById(id);
        transaction.setPaymentStatus(Payment.PAID);
        transaction.setTransactionStatus(TrxStatus.DONE);
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

        return TransactionResponse.builder()
                .id(transaction.getId())
                .quantity(transaction.getQuantity())
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
