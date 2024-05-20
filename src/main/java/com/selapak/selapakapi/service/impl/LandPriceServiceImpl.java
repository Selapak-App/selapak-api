package com.selapak.selapakapi.service.impl;

import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.LandPriceNotFoundException;
import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.repository.LandPriceRepository;
import com.selapak.selapakapi.service.LandPriceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandPriceServiceImpl implements LandPriceService {

    private final LandPriceRepository landPriceRepository;

    @Override
    public LandPrice create(LandPrice landPrice) {
        return landPriceRepository.save(landPrice);
    }

    @Override
    public LandPrice getById(String id) {
        return landPriceRepository.findById(id).orElseThrow(() -> new LandPriceNotFoundException());
    }
    
}
