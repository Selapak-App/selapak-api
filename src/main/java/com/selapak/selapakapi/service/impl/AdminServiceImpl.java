package com.selapak.selapakapi.service.impl;

import org.springframework.stereotype.Service;

import com.selapak.exception.AdminNotFoundException;
import com.selapak.selapakapi.model.entity.Admin;
import com.selapak.selapakapi.repository.AdminRepository;
import com.selapak.selapakapi.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin getById(String id) {
        return adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundException());
    }
    
}
