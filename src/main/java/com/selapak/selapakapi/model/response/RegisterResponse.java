package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.constant.ERole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RegisterResponse {

    private String email;
    private ERole role;
    
}
