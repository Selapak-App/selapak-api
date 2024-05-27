package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.entity.UserCredential;
import com.selapak.selapakapi.model.request.MailSenderRequest;
import com.selapak.selapakapi.repository.UserCredentialRepository;
import com.selapak.selapakapi.service.MailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemplateEngine templateEngine;
    @Override
    public String sendEmail(MailSenderRequest mailSenderRequest, Context context) {
        try {
            String newPassword = generateRandomPassword();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            UserCredential user = userCredentialRepository.findByEmail(mailSenderRequest.getEmail()).orElseThrow(
                    () -> new ApplicationException("Data email request not found", "Email tidak ditemukan", HttpStatus.NOT_FOUND)
            );

            if (user != null) {
                UserCredential updatePass = user.toBuilder()
                        .password(passwordEncoder.encode(newPassword))
                        .build();
                userCredentialRepository.save(updatePass);
            }

            helper.setTo(mailSenderRequest.getEmail());
            helper.setSubject(mailSenderRequest.getEmail());
            context.setVariable("newpassword", newPassword);
            String htmlContent = templateEngine.process("template-email", context);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
            return "Berhasil mengirim ke email";
        } catch (MessagingException e) {
            throw new ApplicationException("Data request invalid", "Gagal mengirim ke email",HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
