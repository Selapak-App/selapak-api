package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.Admin;

public interface AdminService {
    
    Admin create(Admin admin);

    Admin getById(String id);

}
