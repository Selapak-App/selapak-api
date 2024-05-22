package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Land not found")
public class LandNotFoundException extends RuntimeException {

    public LandNotFoundException() {

    }

    public LandNotFoundException(String message) {
        super(message);
    }
    
}
