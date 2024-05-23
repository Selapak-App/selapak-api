package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.LandNotFoundException;
import com.selapak.selapakapi.model.entity.*;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.model.response.LandPhotoResponse;
import com.selapak.selapakapi.model.response.LandPriceWithoutLandResponse;
import com.selapak.selapakapi.model.response.LandResponse;
import com.selapak.selapakapi.repository.LandRepository;
import com.selapak.selapakapi.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandRepository landRepository;
    private final LandOwnerService landOwnerService;
    private final LandPriceService landPriceService;
    private final BusinessTypeService businessTypeService;
    private final LandPhotoService landPhotoService;

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

        landPrice = landPriceService.create(landPrice);

        if (land.getLandPrices() == null) {
            land.setLandPrices(new ArrayList<>());
        }
        land.getLandPrices().add(landPrice);

        List<BusinessType> businessTypes = landRequest.getBusinessTypes().stream()
                .map(businessTypesRequest -> businessTypeService.getById(businessTypesRequest.getBusinessTypeId()))
                .toList();

        List<BusinessRecomendation> businessTypesRecomendation = businessTypes.stream()
                .map(businessType -> BusinessRecomendation.builder()
                        .businessType(businessType)
                        .land(land)
                        .build())
                .collect(Collectors.toList());

        List<LandPhotoResponse> photoResponses = landPhotoService.create(land, landRequest);

        land.setBusinessRecomendations(businessTypesRecomendation);
        land.setLandPhotos(photoResponses.stream().map(landPhotoResponse -> LandPhoto.builder()
                .id(landPhotoResponse.getId())
                .imageURL(landPhotoResponse.getImageURL())
                .isActive(landPhotoResponse.getIsActive())
                .build()).toList());

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
    @Transactional(rollbackOn = Exception.class)
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

        if (request.getPrice() != null) {
            for (LandPrice lp : existingLand.getLandPrices()) {
                if (lp.getIsActive()) {
                    lp.setIsActive(false);
                    landPriceService.update(lp);
                }
            }

            LandPrice landPrice = LandPrice.builder()
                    .price(request.getPrice())
                    .land(existingLand)
                    .isActive(true)
                    .build();

            landPriceService.create(landPrice);

            existingLand.getLandPrices().add(landPrice);
        }

        landRepository.saveAndFlush(existingLand);

        return convertToLandResponse(existingLand);
    }

    @Override
    public void deleteById(String id) {
        Land land = getById(id);
        land.setIsActive(false);
        landRepository.saveAndFlush(land);
    }

    @Override
    public void decreaseLandSlotAvailable(String id, Integer quantity) {
        Land land = getById(id);
        land.setSlotAvailable(land.getSlotAvailable() - quantity);
        landRepository.saveAndFlush(land);
    }

    private LandResponse convertToLandResponse(Land land) {
        LandOwnerResponse landOwner = LandOwnerResponse.builder()
                .id(land.getLandOwner().getId())
                .name(land.getLandOwner().getName())
                .email(land.getLandOwner().getEmail())
                .phoneNumber(land.getLandOwner().getPhoneNumber())
                .nik(land.getLandOwner().getNik())
                .isActive(land.getLandOwner().getIsActive())
                .build();

        LandPrice activeLandPrice = land.getLandPrices().stream()
                .filter(LandPrice::getIsActive)
                .findFirst()
                .orElse(null);

        LandPriceWithoutLandResponse landPrice = LandPriceWithoutLandResponse.builder()
                .id(activeLandPrice.getId())
                .price(activeLandPrice.getPrice())
                .isActive(activeLandPrice.getIsActive())
                .build();

        LandResponse landResponse = LandResponse.builder()
                .id(land.getId())
                .landOwner(landOwner)
                .address(land.getAddress())
                .district(land.getDistrict())
                .village(land.getVillage())
                .postalCode(land.getPostalCode())
                .description(land.getDescription())
                .landPrice(landPrice)
                .slotAvailable(land.getSlotAvailable())
                .totalSlot(land.getTotalSlot())
                .slotArea(land.getSlotArea())
                .isActive(land.getIsActive())
                .landPhotos(land.getLandPhotos().stream()
                        .map(landPhoto -> LandPhotoResponse.builder()
                                .id(landPhoto.getId())
                                .imageURL(landPhoto.getImageURL())
                                .isActive(landPhoto.getIsActive())
                                .build()).toList())
                .businessTypes(
                        land.getBusinessRecomendations().stream()
                                .map(BusinessRecomendation::getBusinessType)
                                .toList())
                .build();

        return landResponse;
    }

}
