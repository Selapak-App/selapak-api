package com.selapak.selapakapi.model.request;

import com.selapak.selapakapi.model.entity.Land;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LandPriceUpdateRequest {
    
    private String landId;
    private Long price;
    private Land land;
    
}
