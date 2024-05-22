package com.selapak.selapakapi.service.impl;

import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.LandPriceNotFoundException;
import com.selapak.selapakapi.model.entity.LandOwner;
import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.request.LandPriceRequest;
import com.selapak.selapakapi.model.request.LandPriceUpdateRequest;
import com.selapak.selapakapi.model.response.LandLessResponse;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.model.response.LandPriceResponse;
import com.selapak.selapakapi.repository.LandPriceRepository;
import com.selapak.selapakapi.service.LandPriceService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    public LandPrice update(LandPrice landPrice) {
        return landPriceRepository.saveAndFlush(landPrice);
    }

    @Override
    public LandPriceResponse createWithDto(LandPriceRequest request) {
        LandPrice landPrice = LandPrice.builder()
                .price(request.getPrice())
                .isActive(true)
                .build();

        landPriceRepository.save(landPrice);

        return convertToLandPriceResponse(landPrice);
    }

    @Override
    public LandPriceResponse updateById(LandPriceUpdateRequest request) {
        LandPrice existingLandPrice = landPriceRepository.findById(request.getLandId()).orElse(null);

        if (existingLandPrice != null) {
            existingLandPrice = existingLandPrice.toBuilder()
                    .isActive(false)
                    .build();
            landPriceRepository.saveAndFlush(existingLandPrice);
        }

        LandPrice newLandPrice = LandPrice.builder()
                .price(request.getPrice())
                .land(request.getLand())
                .isActive(true)
                .build();

        landPriceRepository.save(newLandPrice);

        return convertToLandPriceResponse(newLandPrice);
    }

    @Override
    public List<LandPriceResponse> getAll() {
        List<LandPrice> landPrices = landPriceRepository.findAll();
        return landPrices.stream()
                .map(this::convertToLandPriceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        LandPrice landPrice = getById(id);
        landPrice.setIsActive(false);
        landPriceRepository.saveAndFlush(landPrice);
    }

    private LandPriceResponse convertToLandPriceResponse(LandPrice landPrice) {
        LandOwner landOwner = landPrice.getLand().getLandOwner();

        LandOwnerResponse landOwnerResponse = LandOwnerResponse.builder()
                .id(landOwner.getId())
                .name(landOwner.getName())
                .email(landOwner.getEmail())
                .phoneNumber(landOwner.getPhoneNumber())
                .nik(landOwner.getNik())
                .isActive(landOwner.getIsActive())
                .build();

        LandLessResponse land = LandLessResponse.builder()
                .id(landPrice.getLand().getId())
                .landOwner(landOwnerResponse)
                .address(landPrice.getLand().getAddress())
                .district(landPrice.getLand().getDistrict())
                .village(landPrice.getLand().getVillage())
                .postalCode(landPrice.getLand().getPostalCode())
                .description(landPrice.getLand().getDescription())
                .slotArea(landPrice.getLand().getSlotArea())
                .slotAvailable(landPrice.getLand().getSlotAvailable())
                .totalSlot(landPrice.getLand().getTotalSlot())
                .isActive(landPrice.getLand().getIsActive())
                .build();


        return LandPriceResponse.builder()
                .id(landPrice.getId())
                .price(landPrice.getPrice())
                .land(land)
                .isActive(landPrice.getIsActive())
                .build();
    }
}
