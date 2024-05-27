package com.selapak.selapakapi.service.impl;

import java.util.List;

import com.selapak.selapakapi.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.BusinessTypeNotFoundException;
import com.selapak.selapakapi.model.entity.BusinessType;
import com.selapak.selapakapi.model.request.BusinessTypeRequest;
import com.selapak.selapakapi.repository.BusinessTypeRepository;
import com.selapak.selapakapi.service.BusinessTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessTypeServiceImpl implements BusinessTypeService {

    private final BusinessTypeRepository businessTypeRepository;

    @Override
    public BusinessType create(BusinessType businessType) {
        return businessTypeRepository.save(businessType);
    }

    @Override
    public BusinessType createWithDto(BusinessTypeRequest request) {
        BusinessType businessType = BusinessType.builder()
                .name(request.getName())
                .build();

        return businessTypeRepository.save(businessType);
    }

    @Override
    public List<BusinessType> getAll() {
        return businessTypeRepository.findAll();
    }

    @Override
    public BusinessType getById(String id) {
        return businessTypeRepository.findById(id).orElseThrow(() -> new ApplicationException("Data business type request not found", "Data tipe bisnis tidak ditemukan", HttpStatus.NOT_FOUND));
    }

}
