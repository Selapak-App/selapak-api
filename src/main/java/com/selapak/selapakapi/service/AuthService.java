package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.UserCredential;
import com.selapak.selapakapi.model.request.LoginRequest;
import com.selapak.selapakapi.model.request.RegisterAdminRequest;
import com.selapak.selapakapi.model.request.RegisterCustomerRequest;
import com.selapak.selapakapi.model.request.RegisterSuperAdminRequest;
import com.selapak.selapakapi.model.response.LoginResponse;
import com.selapak.selapakapi.model.response.RegisterResponse;

public interface AuthService {

    UserCredential getById(String id);
    
    RegisterResponse registerSuperAdmin(RegisterSuperAdminRequest request);

    RegisterResponse registerAdmin(RegisterAdminRequest request);

    RegisterResponse registerCustomer(RegisterCustomerRequest request);

    LoginResponse loginAdminAndSuperAdmin(LoginRequest request);

    LoginResponse loginCustomer(LoginRequest request);

}
