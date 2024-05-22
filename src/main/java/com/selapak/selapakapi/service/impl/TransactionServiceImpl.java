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
import com.selapak.selapakapi.model.entity.Business;
import com.selapak.selapakapi.model.entity.BusinessType;
import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.entity.RentPeriod;
import com.selapak.selapakapi.model.entity.Transaction;
import com.selapak.selapakapi.model.request.TransactionChangeStatusRequest;
import com.selapak.selapakapi.model.request.TransactionRequest;
import com.selapak.selapakapi.model.response.TransactionResponse;
import com.selapak.selapakapi.repository.TransactionRepository;
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
                .transactionStatus(TrxStatus.PENDING)
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
    public void dealTransaction(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void payTransaction(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void surveyTransaction(String id, TransactionChangeStatusRequest request) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public TransactionResponse verifyApproveTransaction(String id, TransactionChangeStatusRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TransactionResponse verifyRejectTransaction(String id, TransactionChangeStatusRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .quantity(transaction.getQuantity())
                .verifyBy(transaction.getVerifiedBy())
                .verifyStatus(transaction.getVerifyStatus().toString())
                .isSurveyed(transaction.getIsSurveyed())
                .surveyStatus(transaction.getSurveyStatus().toString())
                .paymentStatus(transaction.getPaymentStatus().toString())
                .transactionStatus(transaction.getTransactionStatus().toString())
                .customer(transaction.getCustomer())
                .rentPeriod(transaction.getRentPeriod())
                .landPrice(transaction.getLandPrice())
                .business(transaction.getBusiness())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .isActive(transaction.getIsActive())
                .build();
    }
    
}
