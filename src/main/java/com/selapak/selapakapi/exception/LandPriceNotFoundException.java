package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Land price not found")
public class LandPriceNotFoundException extends RuntimeException {

    public LandPriceNotFoundException() {
        
    }

    public LandPriceNotFoundException(String message) {
        super(message);
    }
    
}
