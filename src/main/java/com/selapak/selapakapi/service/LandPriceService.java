package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.request.LandPriceRequest;
import com.selapak.selapakapi.model.request.LandPriceUpdateRequest;

import java.util.List;

public interface LandPriceService {
    
    LandPrice getById(String id);

    LandPrice create(LandPrice landPrice);

    LandPrice createWithDto(LandPriceRequest request);

    LandPrice updateById(LandPriceUpdateRequest request);

    List<LandPrice> getAll();

}
