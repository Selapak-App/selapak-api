package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.model.entity.Admin;
import com.selapak.selapakapi.model.entity.Business;
import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.entity.RentPeriod;

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
    private Admin verifyBy;
    private Customer customer;
    private RentPeriod rentPeriod;
    private LandPrice landPrice;
    private Business business;
    private Long createdAt;
    private Long updatedAt;
    private Boolean isActive;
    
}
