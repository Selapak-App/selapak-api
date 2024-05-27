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
    private AdminResponse verifyBy;
    private CustomerResponse customer;
    private RentPeriodResponse rentPeriod;
    private LandPriceResponse landPrice;
    private BusinessResponse business;
    private Long createdAt;
    private Long updatedAt;
    private Boolean isActive;
    private Long totalPayment;
    
}
