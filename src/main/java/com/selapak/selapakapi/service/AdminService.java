package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.Admin;
import com.selapak.selapakapi.model.response.AdminResponse;
import com.selapak.selapakapi.model.response.AllAdminResponse;
import org.springframework.data.domain.Page;

public interface AdminService {
    
    Admin create(Admin admin);

    Admin getById(String id);

    Page<AllAdminResponse> getAll(Integer page, Integer size);

    AllAdminResponse deactiveAdmin(String adminId);
    AllAdminResponse activeAdmin(String adminId);

}
