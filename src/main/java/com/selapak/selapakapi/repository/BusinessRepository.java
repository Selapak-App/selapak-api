package com.selapak.selapakapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selapak.selapakapi.model.entity.Business;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {
    
}
