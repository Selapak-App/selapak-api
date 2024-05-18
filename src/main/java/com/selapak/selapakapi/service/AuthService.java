package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.request.LoginRequest;
import com.selapak.selapakapi.model.request.RegisterAdminRequest;
import com.selapak.selapakapi.model.request.RegisterCustomerRequest;
import com.selapak.selapakapi.model.response.LoginResponse;
import com.selapak.selapakapi.model.response.RegisterResponse;

public interface AuthService {
    
    RegisterResponse registerAdmin(RegisterAdminRequest request);

    RegisterResponse registerCustomer(RegisterCustomerRequest request);

    LoginResponse login(LoginRequest request);

}
