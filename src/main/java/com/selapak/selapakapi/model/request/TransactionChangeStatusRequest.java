package com.selapak.selapakapi.model.request;

import com.selapak.selapakapi.constant.Payment;
import com.selapak.selapakapi.constant.SurveyStatus;
import com.selapak.selapakapi.constant.TrxStatus;
import com.selapak.selapakapi.constant.Verify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionChangeStatusRequest {

    private String adminId;
    private Verify verifyStatus;
    private Boolean isSurveyed;
    private SurveyStatus surveyStatus;
    private Payment paymentStatus;
    private TrxStatus transactionStatus;
    
}
