package com.selapak.selapakapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionRequest {

    private Integer quantity;
    private String adminId;
    private String customerId;
    private String rentPeriodId;
    private String landPriceId;
    private String businessName;
    private String businessDescription;
    private String businessType;
    
}
