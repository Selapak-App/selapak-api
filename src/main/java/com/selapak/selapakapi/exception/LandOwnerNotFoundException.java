package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Land owner not found")
public class LandOwnerNotFoundException extends RuntimeException {

    public LandOwnerNotFoundException() {

    }

    public LandOwnerNotFoundException(String message) {
        super(message);
    }
    
}
