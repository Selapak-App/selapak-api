package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.UserCredential;
import com.selapak.selapakapi.model.request.*;
import com.selapak.selapakapi.model.response.LoginResponse;
import com.selapak.selapakapi.model.response.RegisterResponse;
import com.selapak.selapakapi.model.response.UpdatePasswordResponse;

public interface AuthService {

    UserCredential getById(String id);
    
    RegisterResponse registerSuperAdmin(RegisterSuperAdminRequest request);

    RegisterResponse registerAdmin(RegisterAdminRequest request);

    RegisterResponse registerCustomer(RegisterCustomerRequest request);

    LoginResponse loginAdminAndSuperAdmin(LoginRequest request);

    LoginResponse loginCustomer(LoginRequest request);

    UpdatePasswordResponse update (String id, UpdatePasswordRequest updatePasswordRequest);

}
