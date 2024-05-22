package com.selapak.selapakapi.service;

import org.springframework.data.domain.Page;

import com.selapak.selapakapi.model.entity.Transaction;
import com.selapak.selapakapi.model.request.TransactionRequest;
import com.selapak.selapakapi.model.response.TransactionResponse;

public interface TransactionService {

    Transaction getById(String id);
    
    TransactionResponse create(TransactionRequest request);

    TransactionResponse getByIdWithDto(String id);

    Page<TransactionResponse> getAllWithDto(Integer page, Integer size);

    void deleteById(String id);

}