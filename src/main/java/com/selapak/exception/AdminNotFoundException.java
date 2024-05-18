package com.selapak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Admin not found")
public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException() {

    }

    public AdminNotFoundException(String message) {
        super(message);
    }
    
}
