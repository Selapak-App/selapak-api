package com.selapak.selapakapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selapak.selapakapi.model.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
}
