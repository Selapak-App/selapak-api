package com.selapak.selapakapi.service;


import com.selapak.selapakapi.model.request.MailSenderRequest;
import org.thymeleaf.context.Context;

public interface MailSenderService {

    String sendEmail(MailSenderRequest mailSenderRequest, Context context);
}
