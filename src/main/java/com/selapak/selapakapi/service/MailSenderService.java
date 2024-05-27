package com.selapak.selapakapi.service;


import com.selapak.selapakapi.model.request.MailSenderRequest;

public interface MailSenderService {

    String sendEmail(MailSenderRequest mailSenderRequest);
}
