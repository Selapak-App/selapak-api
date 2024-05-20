package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.constant.ERole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class LoginResponse {

    private String id;
    private String email;
    private ERole role;
    private String token;
    
}
