package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.LandNotFoundException;
import com.selapak.selapakapi.model.entity.*;
import com.selapak.selapakapi.model.request.LandPriceUpdateRequest;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandResponse;
import com.selapak.selapakapi.repository.LandRepository;
import com.selapak.selapakapi.service.BusinessTypeService;
import com.selapak.selapakapi.service.LandOwnerService;
import com.selapak.selapakapi.service.LandPriceService;
import com.selapak.selapakapi.service.LandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandRepository landRepository;
    private final LandOwnerService landOwnerService;
    private final LandPriceService landPriceService;
    private final BusinessTypeService businessTypeService;

    @Override
    public Land getById(String id) {
        return landRepository.findById(id).orElseThrow(() -> new LandNotFoundException());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public LandResponse create(LandRequest landRequest) {
        LandOwner landOwner = landOwnerService.getById(landRequest.getLandOwnerId());

        Land land = Land.builder()
                .landOwner(landOwner)
                .address(landRequest.getAddress())
                .district(landRequest.getDistrict())
                .village(landRequest.getVillage())
                .description(landRequest.getDescription())
                .postalCode(landRequest.getPostalCode())
                .slotArea(landRequest.getSlotArea())
                .slotAvailable(landRequest.getSlotAvailable())
                .totalSlot(landRequest.getTotalSlot())
                .isActive(true)
                .build();

        landRepository.saveAndFlush(land);

        LandPrice landPrice = LandPrice.builder()
                .price(landRequest.getPrice())
                .land(land)
                .isActive(true)
                .build();

        landPriceService.create(landPrice);

        List<BusinessType> businessTypes = landRequest.getBusinessTypes().stream()
                .map(businessTypesRequest -> businessTypeService.getById(businessTypesRequest.getBusinessTypeId()))
                .toList();

        List<BusinessRecomendation> businessTypesRecomendation = businessTypes.stream()
                .map(businessType -> BusinessRecomendation.builder()
                        .businessType(businessType)
                        .land(land)
                        .build())
                .collect(Collectors.toList());

        land.setBusinessRecomendations(businessTypesRecomendation);
        land.setLandPrice(landPrice);

        landRepository.saveAndFlush(land);

        return convertToLandResponse(land);
    }

    @Override
    public LandResponse getByIdWithDto(String id) {
        Land land = getById(id);

        return convertToLandResponse(land);
    }

    @Override
    public Page<LandResponse> getAll(Integer page, Integer size) {
        Page<Land> lands = landRepository.findAll(PageRequest.of(page, size));

        return lands.map(this::convertToLandResponse);
    }

    @Override
    public LandResponse updateById(String id, LandRequest request) {
        Land existingLand = getById(id);

        LandOwner landOwner = landOwnerService.getById(request.getLandOwnerId());

        existingLand = existingLand.toBuilder()
                .landOwner(landOwner)
                .address(request.getAddress())
                .district(request.getDistrict())
                .village(request.getVillage())
                .description(request.getDescription())
                .postalCode(request.getPostalCode())
                .slotArea(request.getSlotArea())
                .slotAvailable(request.getSlotAvailable())
                .totalSlot(request.getTotalSlot())
                .isActive(true)
                .build();
        landRepository.saveAndFlush(existingLand);

        LandPriceUpdateRequest landPrice = LandPriceUpdateRequest.builder()
                .landId(existingLand.getLandPrice().getId())
                .price(request.getPrice())
                .build();
        
        landPriceService.updateById(landPrice);

        return convertToLandResponse(existingLand);
    }

    @Override
    public void deleteById(String id) {
        Land land = getById(id);
        land.setIsActive(false);
        landRepository.saveAndFlush(land);
    }

    private LandResponse convertToLandResponse(Land land) {
        LandResponse landResponse = LandResponse.builder()
                .id(land.getId())
                .landOwnerId(land.getLandOwner().getId())
                .address(land.getAddress())
                .district(land.getDistrict())
                .village(land.getVillage())
                .postalCode(land.getPostalCode())
                .description(land.getDescription())
                .price(land.getLandPrice().getPrice())
                .slotAvailable(land.getSlotAvailable())
                .totalSlot(land.getTotalSlot())
                .slotArea(land.getSlotArea())
                .isActive(land.getIsActive())
                .businessTypes(land.getBusinessRecomendations().stream().map(BusinessRecomendation::getBusinessType).toList())
                .build();

        return landResponse;
    }

}
