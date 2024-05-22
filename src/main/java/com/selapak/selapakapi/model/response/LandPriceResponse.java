package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.model.entity.Land;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LandPriceResponse {

    private String id;
    private Long price;
    private Land land;
    private Boolean isActive;
    
}
