package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.constant.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdatePasswordResponse {
    private String email;
    private final String MESSAGE = "Kata Sandi berhasil diupdate";
}
