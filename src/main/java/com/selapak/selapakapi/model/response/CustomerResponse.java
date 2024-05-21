package com.selapak.selapakapi.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String gender;
    private String nik;
    private Boolean isActive;
    private LocalDateTime updatedAt;
    
}
