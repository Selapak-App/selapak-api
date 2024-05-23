package com.selapak.selapakapi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.LandOwnerNotFoundException;
import com.selapak.selapakapi.model.entity.LandOwner;
import com.selapak.selapakapi.model.request.LandOwnerRequest;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.repository.LandOwnerRepository;
import com.selapak.selapakapi.service.LandOwnerService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandOwnerServiceImpl implements LandOwnerService {

    private final LandOwnerRepository landOwnerRepository;

    @Override
    public LandOwner getById(String id) {
        return landOwnerRepository.findById(id).orElseThrow(() -> new LandOwnerNotFoundException());
    }

    @Override
    public LandOwnerResponse getByIdWithDto(String id) {
        LandOwner landOwner = getById(id);

        return convertToLandOwnerResponse(landOwner);
    }

    @Override
    public Page<LandOwnerResponse> getAllWithDto(Integer page, Integer size) {
        Page<LandOwner> landOwners = landOwnerRepository.findAll(PageRequest.of(page, size));

        return landOwners.map(this::convertToLandOwnerResponse);
    }

    @Override
    public List<LandOwner> getAll() {
        List<LandOwner> landOwners = landOwnerRepository.findAll();
        return landOwners;
    }

    @Override
    public LandOwnerResponse create(LandOwnerRequest request) {
        LandOwner landOwner = LandOwner.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .nik(request.getNik())
                .isActive(true)
                .build();
        landOwnerRepository.save(landOwner);

        return convertToLandOwnerResponse(landOwner);
    }

    @Override
    public LandOwnerResponse updateById(String id, LandOwnerRequest request) {
        LandOwner existingLandOwner = getById(id);

        existingLandOwner = existingLandOwner.toBuilder()
                .id(existingLandOwner.getId())
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .nik(request.getNik())
                .isActive(existingLandOwner.getIsActive())
                .build();
        landOwnerRepository.save(existingLandOwner);

        return convertToLandOwnerResponse(existingLandOwner);
    }

    @Override
    public void deleteById(String id) {
        LandOwner landOwner = getById(id);
        landOwner.setIsActive(false);
        landOwnerRepository.save(landOwner);       
    }

    private LandOwnerResponse convertToLandOwnerResponse(LandOwner landOwner) {
        return LandOwnerResponse.builder()
                .id(landOwner.getId())
                .name(landOwner.getName())
                .email(landOwner.getEmail())
                .phoneNumber(landOwner.getPhoneNumber())
                .nik(landOwner.getNik())
                .isActive(landOwner.getIsActive())
                .build();
    }

}
