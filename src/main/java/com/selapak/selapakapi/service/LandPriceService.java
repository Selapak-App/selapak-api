package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.request.LandPriceRequest;
import com.selapak.selapakapi.model.request.LandPriceUpdateRequest;
import com.selapak.selapakapi.model.response.LandPriceResponse;

import org.springframework.data.domain.Page;

public interface LandPriceService {
    
    LandPrice getById(String id);

    LandPrice create(LandPrice landPrice);

    LandPrice update(LandPrice landPrice);

    LandPriceResponse createWithDto(LandPriceRequest request);

    LandPriceResponse updateById(LandPriceUpdateRequest request);

    Page<LandPriceResponse> getAll(Integer page, Integer size);

    void deleteById(String id);

}
