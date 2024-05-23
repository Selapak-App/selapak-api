package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.model.entity.Land;
import com.selapak.selapakapi.model.entity.LandOwner;
import com.selapak.selapakapi.model.entity.Transaction;
import com.selapak.selapakapi.model.response.DashboardResponse;
import com.selapak.selapakapi.service.DashboardService;
import com.selapak.selapakapi.service.LandOwnerService;
import com.selapak.selapakapi.service.LandService;
import com.selapak.selapakapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TransactionService transactionService;
    private final LandOwnerService landOwnerService;
    private final LandService landService;
    @Override
    public DashboardResponse dashboardData() {
        List<Transaction> transactions = transactionService.getAll();
        List<LandOwner> landOwners = landOwnerService.getAll();
        List<Land> lands = landService.getAll();
        return DashboardResponse.builder()
                .totalTransaction(transactions.size())
                .totalLandOwner(landOwners.size())
                .totalLand(lands.size())
                .build();
    }
}
