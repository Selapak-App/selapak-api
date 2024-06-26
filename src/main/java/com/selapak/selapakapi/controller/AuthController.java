package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.model.request.*;
import com.selapak.selapakapi.model.response.UpdatePasswordResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.LoginResponse;
import com.selapak.selapakapi.model.response.RegisterResponse;
import com.selapak.selapakapi.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AppPath.REGISTER_ADMIN_PATH)
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterAdminRequest request) {
        RegisterResponse registerResponse = authService.registerAdmin(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Admin created successfully.")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping(AppPath.REGISTER_PATH)
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        RegisterResponse registerResponse = authService.registerCustomer(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Customer created successfully.")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(AppPath.REGISTER_SUPER_ADMIN_PATH)
    public ResponseEntity<?> registerSuperAdmin(@Valid @RequestBody RegisterSuperAdminRequest request) {
        RegisterResponse registerResponse = authService.registerSuperAdmin(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Super admin created successfully.")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping(AppPath.LOGIN_ADMIN_PATH)
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginAdminAndSuperAdmin(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successfully.")
                .data(loginResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(AppPath.LOGIN_PATH)
    public ResponseEntity<?> loginCustomer(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginCustomer(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successfully.")
                .data(loginResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_PASSWORD)
    public ResponseEntity<?> updatePassword(@PathVariable String id, @RequestBody UpdatePasswordRequest request) {
        UpdatePasswordResponse updateResponse = authService.update(id, request);
        CommonResponse<UpdatePasswordResponse> response = CommonResponse.<UpdatePasswordResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Update successfully.")
                .data(updateResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
