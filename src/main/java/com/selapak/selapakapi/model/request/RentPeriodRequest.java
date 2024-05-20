package com.selapak.selapakapi.model.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RentPeriodRequest {

    @Positive(message = "Period must be greater than 0")
    private Integer period;
    
}
