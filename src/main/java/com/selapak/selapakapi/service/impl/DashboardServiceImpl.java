package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.constant.TrxStatus;
import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.model.entity.Land;
import com.selapak.selapakapi.model.entity.LandOwner;
import com.selapak.selapakapi.model.entity.Transaction;
import com.selapak.selapakapi.model.response.DashboardResponse;
import com.selapak.selapakapi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TransactionService transactionService;
    private final LandOwnerService landOwnerService;
    private final LandService landService;
    private final CustomerService customerService;
    @Override
    public DashboardResponse dashboardData() {
        List<Transaction> transactions = transactionService.getAll();
        List<Transaction> transactionsOnProgress = transactions.stream()
                .filter(transaction -> transaction.getTransactionStatus().equals(TrxStatus.ON_PROGRESS))
                .toList();
        List<Transaction> transactionsFail = transactions.stream()
                .filter(transaction -> transaction.getTransactionStatus().equals(TrxStatus.FAILED))
                .toList();
        List<Transaction> transactionsSuccess = transactions.stream()
                .filter(transaction -> transaction.getTransactionStatus().equals(TrxStatus.DONE))
                .toList();
        List<LandOwner> landOwners = landOwnerService.getAll();
        List<Land> lands = landService.getAll();
        List<Land> landsAvailable = lands.stream()
                .filter(land -> land.getSlotAvailable() > 0)
                .toList();
        List<Customer> customers = customerService.getAll();
        Map<Customer, List<Transaction>> business = transactions.stream()
                .filter(transaction -> transaction.getTransactionStatus().equals(TrxStatus.DONE))
                .collect(Collectors.groupingBy(Transaction::getCustomer));
        return DashboardResponse.builder()
                .totalTransactionOnProgress(transactionsOnProgress.size())
                .totalTransactionSuccess(transactionsSuccess.size())
                .totalTransactionFail(transactionsFail.size())
                .totalTransaction(transactions.size())
                .totalLandOwner(landOwners.size())
                .totalLand(lands.size())
                .totalLandAvailable(landsAvailable.size())
                .totalBusinessOwner(customers.size())
                .totalBusiness(business.size())
                .build();
    }
}
