package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.SuperAdmin;

public interface SuperAdminService {

    SuperAdmin create(SuperAdmin superAdmin);

    SuperAdmin getById(String id);
    
}
