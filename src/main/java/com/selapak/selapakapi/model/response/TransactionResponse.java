package com.selapak.selapakapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {

    private String id;
    private Integer quantity;
    private String verifyStatus;
    private Boolean isSurveyed;
    private String surveyStatus;
    private String paymentStatus;
    private String transactionStatus;
    private String verifyBy;
    private String customer;
    private Integer rentPeriod;
    private String landAddress;
    private Long landPrice;
    private String businessName; 
    private String businessDescription;
    private String businessType;
    private Long createdAt;
    private Long updatedAt;
    private Boolean isActive;
    
}
