package com.selapak.selapakapi.model.request;

import com.selapak.selapakapi.constant.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    private String id;
    private String fullName;
    private String phoneNumber;
    private Gender gender;
    private String address;
    private String nik;
    
}
