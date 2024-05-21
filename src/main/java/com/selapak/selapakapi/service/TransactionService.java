package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.request.TransactionRequest;
import com.selapak.selapakapi.model.response.TransactionResponse;

public interface TransactionService {
    
    TransactionResponse create(TransactionRequest request);

}
