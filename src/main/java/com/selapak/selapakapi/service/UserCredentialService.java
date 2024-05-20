package com.selapak.selapakapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.selapak.selapakapi.model.entity.AppUser;

public interface UserCredentialService extends UserDetailsService {

    AppUser loadUserByUserId(String id);
    
}
