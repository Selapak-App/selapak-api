package com.selapak.selapakapi.service;

import java.util.List;

import com.selapak.selapakapi.model.entity.Business;

public interface BusinessService {
    
    Business getById(String id);

    Business create(Business business);

    List<Business> getAll();

}
