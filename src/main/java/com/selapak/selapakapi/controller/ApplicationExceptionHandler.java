package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException applicationException, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                applicationException.getErrorCode(),
                applicationException.getMessage(),
                applicationException.getHttpStatus().value(),
                applicationException.getHttpStatus().name(),
                request.getRequestURI(),
                request.getMethod()
        );

        return new ResponseEntity<>(errorResponse, applicationException.getHttpStatus());
    }
}