package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.constant.AdminStatus;
import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.response.AllAdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        return adminRepository.findByIdAndIsActive(id, true).orElseThrow(() -> new ApplicationException("Data admin request not found", "Data Admin Tidak Ditemukan", HttpStatus.NOT_FOUND));
    }

    public Admin getByIdDeactive(String id){
        return adminRepository.findByIdAndIsActive(id, false).orElseThrow(() -> new ApplicationException("Data admin request not found", "Data Admin Tidak Ditemukan", HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<AllAdminResponse> getAll(Integer page, Integer size) {
        Page<Admin> adminPage = adminRepository.findAll(PageRequest.of(page,size));
        Page<AllAdminResponse> adminResponses = adminPage.map(admin -> {
            String status = admin.getIsActive() ? "INACTIVE" : "DEACTIVE";
            return AllAdminResponse.builder()
                    .id(admin.getId())
                    .name(admin.getName())
                    .email(admin.getEmail())
                    .isActive(admin.getIsActive())
                    .status(status)
                    .build();
        });

        return adminResponses;
    }

    @Override
    public AllAdminResponse deactiveAdmin(String adminId) {
        Admin getAdminId = this.getById(adminId);
        Admin updateAdmin = getAdminId.toBuilder()
                .isActive(false)
                .build();
        adminRepository.save(updateAdmin);

        String status = updateAdmin.getIsActive() ? "INACTIVE" : "DEACTIVE";
        return AllAdminResponse.builder()
                .id(updateAdmin.getId())
                .name(updateAdmin.getName())
                .email(updateAdmin.getEmail())
                .isActive(updateAdmin.getIsActive())
                .status(status)
                .build();
    }

    @Override
    public AllAdminResponse activeAdmin(String adminId) {
        Admin getAdminId = this.getByIdDeactive(adminId);
        Admin updateAdmin = getAdminId.toBuilder()
                .isActive(true)
                .build();
        adminRepository.save(updateAdmin);

        String status = updateAdmin.getIsActive() ? "INACTIVE" : "DEACTIVE";
        return AllAdminResponse.builder()
                .id(updateAdmin.getId())
                .name(updateAdmin.getName())
                .email(updateAdmin.getEmail())
                .isActive(updateAdmin.getIsActive())
                .status(status)
                .build();
    }
}
