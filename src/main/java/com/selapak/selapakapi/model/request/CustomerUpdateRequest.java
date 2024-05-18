package com.selapak.selapakapi.model.request;

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
    private String address;
    private String nik;
    
}
