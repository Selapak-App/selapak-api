package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.entity.Customer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.LandOwnerNotFoundException;
import com.selapak.selapakapi.model.entity.LandOwner;
import com.selapak.selapakapi.model.request.LandOwnerRequest;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.repository.LandOwnerRepository;
import com.selapak.selapakapi.service.LandOwnerService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LandOwnerServiceImpl implements LandOwnerService {

    private final LandOwnerRepository landOwnerRepository;

    @Override
    public LandOwner getById(String id) {
        return landOwnerRepository.findById(id).orElseThrow(() -> new ApplicationException("Data land owner request not found", "Pemilik lahan tidak ditemukan", HttpStatus.NOT_FOUND));
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
        try{

            Optional<LandOwner> checkEmail = landOwnerRepository.findByEmail(request.getEmail());
            Optional<LandOwner> checkNomorHandphone = landOwnerRepository.findByPhoneNumber(request.getPhoneNumber());
            Optional<LandOwner> checkNik = landOwnerRepository.findByNik(request.getNik());

            if(checkNik.isPresent()){
                throw new DataIntegrityViolationException("NIK sudah terdaftar");
            }

            if(checkNomorHandphone.isPresent()){
                throw new DataIntegrityViolationException("Nomor hp sudah terdaftar");
            }

            if(checkEmail.isPresent()){
                throw new DataIntegrityViolationException("Email sudah terdaftar");
            }

            LandOwner landOwner = LandOwner.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .nik(request.getNik())
                    .isActive(true)
                    .build();
            landOwnerRepository.save(landOwner);

            return convertToLandOwnerResponse(landOwner);
        }catch(DataIntegrityViolationException e){
            throw new ApplicationException("Data request conflict", e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public LandOwnerResponse updateById(String id, LandOwnerRequest request) {
        LandOwner existingLandOwner = getById(id);

        Optional<LandOwner> checkEmail = landOwnerRepository.findByEmail(request.getEmail());
        Optional<LandOwner> checkNomorHandphone = landOwnerRepository.findByPhoneNumber(request.getPhoneNumber());
        Optional<LandOwner> checkNik = landOwnerRepository.findByNik(request.getNik());

        if(checkNik.isPresent() && !existingLandOwner.getNik().equals(request.getNik()) && checkNomorHandphone.isPresent() && !existingLandOwner.getPhoneNumber().equals(request.getPhoneNumber()) && checkEmail.isPresent() && !existingLandOwner.getEmail().equals(request.getEmail())){
            throw new DataIntegrityViolationException("Email, NIK, dan Nomor hp sudah terdaftar");
        }

        if(checkNik.isPresent() && !existingLandOwner.getNik().equals(request.getNik())){
            throw new DataIntegrityViolationException("NIK sudah terdaftar");
        }

        if(checkNomorHandphone.isPresent() && !existingLandOwner.getPhoneNumber().equals(request.getPhoneNumber())){
            throw new DataIntegrityViolationException("Nomor hp sudah terdaftar");
        }

        if(checkEmail.isPresent() && !existingLandOwner.getEmail().equals(request.getEmail())){
            throw new DataIntegrityViolationException("Email sudah terdaftar");
        }

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
