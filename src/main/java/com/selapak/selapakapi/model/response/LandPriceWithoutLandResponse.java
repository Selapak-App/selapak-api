package com.selapak.selapakapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LandPriceWithoutLandResponse {

    private String id;
    private Long price;
    private Boolean isActive;
    
}
