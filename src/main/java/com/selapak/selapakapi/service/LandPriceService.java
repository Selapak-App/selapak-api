package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.request.LandPriceRequest;
import com.selapak.selapakapi.model.request.LandPriceUpdateRequest;
import com.selapak.selapakapi.model.response.LandPriceResponse;

import java.util.List;

public interface LandPriceService {
    
    LandPrice getById(String id);

    LandPrice create(LandPrice landPrice);

    LandPrice update(LandPrice landPrice);

    LandPrice createWithDto(LandPriceRequest request);

    LandPrice updateById(LandPriceUpdateRequest request);

    List<LandPriceResponse> getAll();

    void deleteById(String id);

}
