package com.selapak.selapakapi.service;

import com.selapak.selapakapi.constant.ERole;
import com.selapak.selapakapi.model.entity.Role;

public interface RoleService {
    
    Role getOrSave(ERole role);

}
