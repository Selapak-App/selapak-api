package com.selapak.selapakapi.service.impl;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.selapak.selapakapi.constant.ERole;
import com.selapak.selapakapi.exception.UserCredentialNotFoundException;
import com.selapak.selapakapi.model.entity.Admin;
import com.selapak.selapakapi.model.entity.AppUser;
import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.model.entity.Role;
import com.selapak.selapakapi.model.entity.SuperAdmin;
import com.selapak.selapakapi.model.entity.UserCredential;
import com.selapak.selapakapi.model.request.LoginRequest;
import com.selapak.selapakapi.model.request.RegisterAdminRequest;
import com.selapak.selapakapi.model.request.RegisterCustomerRequest;
import com.selapak.selapakapi.model.request.RegisterSuperAdminRequest;
import com.selapak.selapakapi.model.response.LoginResponse;
import com.selapak.selapakapi.model.response.RegisterResponse;
import com.selapak.selapakapi.repository.UserCredentialRepository;
import com.selapak.selapakapi.security.JwtUtil;
import com.selapak.selapakapi.service.AdminService;
import com.selapak.selapakapi.service.AuthService;
import com.selapak.selapakapi.service.CustomerService;
import com.selapak.selapakapi.service.RoleService;
import com.selapak.selapakapi.service.SuperAdminService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserCredentialRepository userCredentialRepository;
    private final AdminService adminService;
    private final SuperAdminService superAdminService;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse registerSuperAdmin(RegisterSuperAdminRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);

            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            SuperAdmin superAdmin = SuperAdmin.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .userCredential(userCredential)
                    .build();
            superAdminService.create(superAdmin);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getName())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerAdmin(RegisterAdminRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);

            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = Admin.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .isActive(true)
                    .userCredential(userCredential)
                    .build();
            adminService.create(admin);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getName())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(RegisterCustomerRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);

            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Customer customer = Customer.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .gender(request.getGender())
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .userCredential(userCredential)
                    .build();
            customerService.create(customer);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getName())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    @Override
    public LoginResponse loginAdminAndSuperAdmin(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail().toLowerCase(),
                request.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        UserCredential userCredential = getById(appUser.getId());
    
        if (appUser.getRole().equals(ERole.ROLE_ADMIN)) {
            Admin admin = adminService.getById(userCredential.getAdmin().getId());

            return LoginResponse.builder()
                    .id(admin.getId())
                    .email(appUser.getEmail())
                    .role(appUser.getRole())
                    .token(token)
                    .build();
        } else {
            SuperAdmin superAdmin = superAdminService.getById(userCredential.getSuperAdmin().getId());

            return LoginResponse.builder()
                    .id(superAdmin.getId())
                    .email(appUser.getEmail())
                    .role(appUser.getRole())
                    .token(token)
                    .build();
        }
    }

    @Override
    public LoginResponse loginCustomer(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail().toLowerCase(),
                request.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        UserCredential userCredential = getById(appUser.getId());
        Customer customer = customerService.getById(userCredential.getCustomer().getId());

        return LoginResponse.builder()
                .id(customer.getId())
                .email(appUser.getEmail())
                .role(appUser.getRole())
                .token(token)
                .build();
    }

    @Override
    public UserCredential getById(String id) {
        return userCredentialRepository.findById(id).orElseThrow(() -> new UserCredentialNotFoundException());
    }

}
