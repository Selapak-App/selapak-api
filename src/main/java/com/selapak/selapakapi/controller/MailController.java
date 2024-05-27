package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.request.MailSenderRequest;
import com.selapak.selapakapi.model.request.RentPeriodRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.RentPeriodResponse;
import com.selapak.selapakapi.service.MailSenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.SEND_EMAIL)
public class MailController {

    private final MailSenderService mailSenderService;
    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody MailSenderRequest mailSenderRequest) {
        String send = mailSenderService.sendEmail(mailSenderRequest);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Send Email successfully.")
                .data(send)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
