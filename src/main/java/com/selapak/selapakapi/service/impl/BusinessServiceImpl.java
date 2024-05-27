package com.selapak.selapakapi.service.impl;

import java.util.List;

import com.selapak.selapakapi.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.BusinessNotFoundException;
import com.selapak.selapakapi.model.entity.Business;
import com.selapak.selapakapi.repository.BusinessRepository;
import com.selapak.selapakapi.service.BusinessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;

    @Override
    public Business create(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Business getById(String id) {
        return businessRepository.findById(id).orElseThrow(() -> new ApplicationException("Data business request not found", "Data bisnis tidak ditemukan", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Business> getAll() {
        List<Business> businesses = businessRepository.findAll();
        return businesses;
    }

}
