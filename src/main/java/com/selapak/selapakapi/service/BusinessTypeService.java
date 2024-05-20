package com.selapak.selapakapi.service;

import java.util.List;

import com.selapak.selapakapi.model.entity.BusinessType;
import com.selapak.selapakapi.model.request.BusinessTypeRequest;

public interface BusinessTypeService {
    
    BusinessType create(BusinessType businessType);

    BusinessType createWithDto(BusinessTypeRequest request);

    BusinessType getById(String id);

    List<BusinessType> getAll();

}
