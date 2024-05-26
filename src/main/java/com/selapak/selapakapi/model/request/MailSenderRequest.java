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
    private final String MESSAGE = "Kata Sandi Anda berhasil direset, mohon untuk tidak disebarluaskan kata sandi ini dan untuk mencegah hal-hal yang tidak diinginkan mohon untuk mengganti kata sandi Anda. Silakan masuk menggunakan kata sandi berikut ini :";
}
