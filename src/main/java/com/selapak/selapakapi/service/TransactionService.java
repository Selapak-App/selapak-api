package com.selapak.selapakapi.service;

import org.springframework.data.domain.Page;

import com.selapak.selapakapi.model.entity.Transaction;
import com.selapak.selapakapi.model.request.TransactionRequest;
import com.selapak.selapakapi.model.request.TransactionVerifyRequest;
import com.selapak.selapakapi.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    Transaction getById(String id);
    
    TransactionResponse create(TransactionRequest request);

    TransactionResponse getByIdWithDto(String id);

    Page<TransactionResponse> getAllWithDto(Integer page, Integer size);
    List<Transaction> getAll();

    void deleteById(String id);

    TransactionResponse verifyApproveTransaction(String id, TransactionVerifyRequest request);

    TransactionResponse verifyRejectTransaction(String id, TransactionVerifyRequest request);

    void doneSurveyLandTransaction(String id);

    TransactionResponse acceptTransactionAfterSurveyByCustomer(String id);

    TransactionResponse declineTransactionAfterSurveyByCustomer(String id);

    TransactionResponse payTransaction(String id);



}
