package com.selapak.selapakapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User credential not found")
public class UserCredentialNotFoundException extends RuntimeException {

    public UserCredentialNotFoundException() {

    }

    public UserCredentialNotFoundException(String message) {
        super(message);
    }
    
}
