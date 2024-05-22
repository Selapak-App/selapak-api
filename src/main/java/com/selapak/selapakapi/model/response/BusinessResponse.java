package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.model.entity.BusinessType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BusinessResponse {

    private String id;
    private String businessName;
    private String descripttion;
    private Boolean isActive;
    private String businessType;
    
}
