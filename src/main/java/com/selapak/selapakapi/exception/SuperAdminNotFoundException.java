package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Super Admin Not Found")
public class SuperAdminNotFoundException extends RuntimeException {

    public SuperAdminNotFoundException() {

    }

    public SuperAdminNotFoundException(String message) {
        super(message);
    }
    
}
