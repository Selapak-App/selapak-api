package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Transaction not found")
public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException() {
        
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
    
}
