package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.entity.UserCredential;
import com.selapak.selapakapi.model.request.MailSenderRequest;
import com.selapak.selapakapi.repository.UserCredentialRepository;
import com.selapak.selapakapi.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;
    private final UserCredentialRepository userCredentialRepository;
    @Override
    public String sendEmail(MailSenderRequest mailSenderRequest) {
        String newPassword = generateRandomPassword();

        UserCredential user = userCredentialRepository.findByEmail(mailSenderRequest.getEmail()).orElseThrow(
                () -> new ApplicationException("Invalid Email", "Email tidak ditemukan", HttpStatus.NOT_FOUND)
        );

        if (user != null) {
            user.toBuilder()
                .password(newPassword)
                .build();
            userCredentialRepository.save(user);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailSenderRequest.getEmail());
        message.setSubject(mailSenderRequest.getSUBJECT());
        message.setText(mailSenderRequest.getMESSAGE()
                + newPassword);
        javaMailSender.send(message);

        return "Berhasil mengirim ke email";
    }

    private String generateRandomPassword() {
        String charactersUpper = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
        String charactersLower = charactersUpper.toLowerCase();
        String numbers = "0123456789";
        String characters = charactersUpper + charactersLower + numbers;

        StringBuilder builder = new StringBuilder(8);
        Random random = new Random();

        for(int i = 0; i < 8; i++){
            int index = random.nextInt(characters.length());
            builder.append(characters.charAt(index));
        }

        return builder.toString();
    }
}
