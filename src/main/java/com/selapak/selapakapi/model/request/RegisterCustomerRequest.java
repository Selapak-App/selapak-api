package com.selapak.selapakapi.model.request;

import com.selapak.selapakapi.constant.Gender;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCustomerRequest {

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Email is required.")
    private String email;

    // @NotBlank(message = "Gender is required.")
    private Gender gender;

    private String password;
    
}
