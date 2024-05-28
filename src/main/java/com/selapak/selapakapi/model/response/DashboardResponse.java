package com.selapak.selapakapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DashboardResponse {
    private int totalLandOwner;
    private int totalLand;
    private int totalLandAvailable;
    private int totalBusinessOwner;
    private int totalBusiness;
    private int totalTransactionOnProgress;
    private int totalTransactionSuccess;
    private int totalTransactionFail;
    private int totalTransaction;
}
