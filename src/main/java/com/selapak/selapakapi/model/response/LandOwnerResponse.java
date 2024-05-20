package com.selapak.selapakapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LandOwnerResponse {

    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String nik;
    private Boolean isActive;
    
}
