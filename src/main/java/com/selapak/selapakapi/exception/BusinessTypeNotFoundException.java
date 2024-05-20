package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Business type not found")
public class BusinessTypeNotFoundException extends RuntimeException {

    public BusinessTypeNotFoundException() {
        
    }

    public BusinessTypeNotFoundException(String message) {
        super(message);
    }
    
}
