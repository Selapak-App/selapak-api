package com.selapak.selapakapi.model.response;

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
    private LandLessResponse land;
    private Boolean isActive;
    
}
