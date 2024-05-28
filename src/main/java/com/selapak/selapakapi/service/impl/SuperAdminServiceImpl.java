package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.ApplicationException;
import org.springframework.http.HttpStatus;
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
        return superAdminRepository.findById(id).orElseThrow(() -> new ApplicationException("Data super admin request not found", "Data super admin tidak ditemukan", HttpStatus.NOT_FOUND));
    }

}
