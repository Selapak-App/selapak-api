package com.selapak.selapakapi.service.impl;

import java.util.List;

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
        return businessRepository.findById(id).orElseThrow(() -> new BusinessNotFoundException());
    }

    @Override
    public List<Business> getAll() {
        List<Business> businesses = businessRepository.findAll();
        return businesses;
    }

}
