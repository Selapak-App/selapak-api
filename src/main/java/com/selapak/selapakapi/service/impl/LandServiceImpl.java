package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.entity.*;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.model.response.LandPhotoResponse;
import com.selapak.selapakapi.model.response.LandPriceWithoutLandResponse;
import com.selapak.selapakapi.model.response.LandResponse;
import com.selapak.selapakapi.repository.LandRepository;
import com.selapak.selapakapi.service.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
        return landRepository.findById(id).orElseThrow(() -> new ApplicationException("Data land request not found", "Lapak tidak ditemukan", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public LandResponse create(LandRequest landRequest) {
        LandOwner landOwner = landOwnerService.getById(landRequest.getLandOwnerId());

        if(landRequest.getSlotAvailable() > landRequest.getTotalSlot()){
            throw new ApplicationException("Data land request conflict", "slot tersedia tidak boleh lebih besar dari total slot", HttpStatus.CONFLICT);
        }

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

        List<BusinessType> businessTypes = new ArrayList<>();
        if(landRequest.getBusinessTypes() != null){
             businessTypes = landRequest.getBusinessTypes().stream()
                    .map(businessTypesRequest -> businessTypeService.getById(businessTypesRequest.getBusinessTypeId()))
                    .toList();
        }else{
            throw new ApplicationException("Data bad request", "Data tipe bisnis tidak boleh kosong", HttpStatus.BAD_REQUEST);
        }

        List<BusinessRecomendation> businessTypesRecomendation = businessTypes.stream()
                .map(businessType -> BusinessRecomendation.builder()
                        .businessType(businessType)
                        .land(land)
                        .build())
                .collect(Collectors.toList());

        List<LandPhotoResponse> photoResponses = new ArrayList<>();
        if(landRequest.getLandPhotos() != null){
           photoResponses = landPhotoService.create(land, landRequest);
        }else{
            throw new ApplicationException("Data bad request", "Foto land tidak boleh kosong", HttpStatus.BAD_REQUEST);
        }

        land.setBusinessRecomendations(businessTypesRecomendation);
        land.setLandPhotos(photoResponses.stream().map(landPhotoResponse -> LandPhoto.builder()
                .id(landPhotoResponse.getId())
                .imageURL(landPhotoResponse.getImageURL())
                .isActive(landPhotoResponse.getIsActive())
                .build()).toList());

        return convertToLandResponse(land);
    }

    @Override
    public Land updateSlot(Land land) {
        return landRepository.save(land);
    }

    @Override
    public LandResponse getByIdWithDto(String id) {
        Land land = this.getById(id);

        return convertToLandResponse(land);
    }

    @Override
    public Page<LandResponse> getAll(Integer page, Integer size) {
        Page<Land> lands = landRepository.findAll(PageRequest.of(page, size));

        return lands.map(this::convertToLandResponse);
    }

    @Override
    public List<Land> getAll() {
        return landRepository.findAll();
    }

    public static Specification<Land> withAvailableSlots() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("slotAvailable"), 0);
    }

    public static Specification<Land> sortByPrice(Boolean ascending) {
        return (root, query, criteriaBuilder) -> {
            Join<Land, LandPrice> landPriceJoin = root.join("landPrices", JoinType.INNER);
            if(ascending != null){
                query.orderBy(ascending ? criteriaBuilder.asc(landPriceJoin.get("price")) : criteriaBuilder.desc(landPriceJoin.get("price")));
            }
            return criteriaBuilder.conjunction();
        };
    }

    @Override
    public List<LandResponse> getAllLandAvailable(Boolean sortByHighestPrice) {
        Specification<Land> availabilitySpec = withAvailableSlots();

        if (sortByHighestPrice != null) {
            Specification<Land> priceSpec = sortByPrice(sortByHighestPrice);
            availabilitySpec = availabilitySpec.and(priceSpec);
        }

        List<Land> lands = landRepository.findAll(availabilitySpec);

        return lands.stream()
                .filter(Land::getIsActive)
                .map(this::convertToLandResponse)
                .toList();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public LandResponse updateById(String id, LandRequest request) {
        Land existingLand = getById(id);
        LandOwner landOwner = landOwnerService.getById(request.getLandOwnerId());
        List<LandPhotoResponse> landPhotos = landPhotoService.getAllPhoto(existingLand.getId());

        existingLand = existingLand.toBuilder()
                .landOwner(landOwner)
                .landPhotos(landPhotos.stream()
                        .map(landPhoto ->LandPhoto.builder()
                                .id(landPhoto.getId())
                                .imageURL(landPhoto.getImageURL())
                                .isActive(landPhoto.getIsActive())
                                .build())
                        .toList())
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
        int newAvailableSlots = land.getSlotAvailable() - quantity;
        if (newAvailableSlots < 0) {
            newAvailableSlots = 0;
        }
        land.setSlotAvailable(newAvailableSlots);
        landRepository.saveAndFlush(land);
    }

    @Override
    public int getAvailableSlots(String landId) {
        Land land = getById(landId);
        return land.getSlotAvailable();
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
