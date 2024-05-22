package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Business not found")
public class BusinessNotFoundException extends RuntimeException {

    public BusinessNotFoundException() {

    }

    public BusinessNotFoundException(String message) {
        super(message);
    }
    
}
