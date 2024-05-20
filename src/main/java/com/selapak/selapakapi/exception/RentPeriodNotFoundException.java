package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Rent period not found")
public class RentPeriodNotFoundException extends RuntimeException {

    public RentPeriodNotFoundException() {

    }

    public RentPeriodNotFoundException(String message) {
        super(message);
    }
    
}
