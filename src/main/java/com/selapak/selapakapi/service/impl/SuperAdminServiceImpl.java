package com.selapak.selapakapi.service.impl;

import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.SuperAdminNotFoundException;
import com.selapak.selapakapi.model.entity.SuperAdmin;
import com.selapak.selapakapi.repository.SuperAdminRepository;
import com.selapak.selapakapi.service.SuperAdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;

    @Override
    public SuperAdmin create(SuperAdmin superAdmin) {
        return superAdminRepository.save(superAdmin);
    }

    @Override
    public SuperAdmin getById(String id) {
        return superAdminRepository.findById(id).orElseThrow(() -> new SuperAdminNotFoundException());
    }

}
