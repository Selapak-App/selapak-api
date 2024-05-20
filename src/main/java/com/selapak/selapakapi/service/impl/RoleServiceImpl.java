package com.selapak.selapakapi.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.selapak.selapakapi.constant.ERole;
import com.selapak.selapakapi.model.entity.Role;
import com.selapak.selapakapi.repository.RoleRepository;
import com.selapak.selapakapi.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = roleRepository.findByName(role);

        if(optionalRole.isPresent()) {
            return optionalRole.get();
        }

        Role newRole = Role.builder()
                .name(role)
                .build();
        
		return roleRepository.saveAndFlush(newRole);
    }

}
