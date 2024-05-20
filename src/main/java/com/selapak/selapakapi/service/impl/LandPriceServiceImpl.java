package com.selapak.selapakapi.service.impl;

import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.LandPriceNotFoundException;
import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.request.LandPriceRequest;
import com.selapak.selapakapi.model.request.LandPriceUpdateRequest;
import com.selapak.selapakapi.repository.LandPriceRepository;
import com.selapak.selapakapi.service.LandPriceService;

import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @Override
    public LandPrice createWithDto(LandPriceRequest request) {
        LandPrice landPrice = LandPrice.builder()
                .price(request.getPrice())
                .isActive(true)
                .build();
                
        return landPriceRepository.save(landPrice);
    }

    @Override
    public LandPrice updateById(LandPriceUpdateRequest request) {
        LandPrice existingLandPrice = getById(request.getLandId());
        existingLandPrice = existingLandPrice.toBuilder()
                .isActive(false)
                .build();
        landPriceRepository.saveAndFlush(existingLandPrice);

        LandPrice newLandPrice = LandPrice.builder()
                .price(request.getPrice())
                .isActive(true)
                .build();

        return landPriceRepository.save(newLandPrice);
    }

    @Override
    public List<LandPrice> getAll() {
        return landPriceRepository.findAll();
    }
}
