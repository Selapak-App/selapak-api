package com.selapak.selapakapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MailSenderRequest {
    private String email;
    private final String SUBJECT = "Kata Sandi Baru Pengguna Selapak APP";
}
