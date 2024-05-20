package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.model.entity.*;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandResponse;
import com.selapak.selapakapi.repository.LandRepository;
import com.selapak.selapakapi.service.BusinessTypeService;
import com.selapak.selapakapi.service.LandOwnerService;
import com.selapak.selapakapi.service.LandPriceService;
import com.selapak.selapakapi.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandRepository landRepository;
    private final LandOwnerService landOwnerService;
    private final LandPriceService landPriceService;
    private final BusinessTypeService businessTypeService;
    @Override
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

        LandPrice landPrice = LandPrice.builder()
                .price(landRequest.getPrice())
                .land(land)
                .build();

        landPriceService.create(landPrice);

        List<BusinessType> businessTypes = landRequest.getBusinessTypes().stream()
                .map(businessTypesRequest -> {
                            BusinessType businessType = businessTypeService.getById(businessTypesRequest.getId());
                            return businessType;
                        }
                ).toList();

        List<BusinessRecomendation> businessTypesRecomendation = businessTypes.stream()
                .map(businessType -> {
                    return BusinessRecomendation.builder()
                            .businessType(businessType)
                            .land(land)
                            .build();
                }).toList();

        land.setBusinessRecomendations(businessTypesRecomendation);
        land.setLandPriceList(List.of(landPrice));

        landRepository.save(land);

        return LandResponse.builder()
                .landOwnerId(landOwner.getId())
                .address(land.getAddress())
                .district(land.getDistrict())
                .village(land.getVillage())
                .postalCode(land.getPostalCode())
                .description(land.getDescription())
                .price(landRequest.getPrice())
                .slotAvailable(land.getSlotAvailable())
                .totalSlot(land.getTotalSlot())
                .slotArea(land.getSlotArea())
                .businessTypes(businessTypes)
                .build();
    }
}
