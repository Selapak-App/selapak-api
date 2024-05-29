package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.exception.ApplicationException;
import com.selapak.selapakapi.model.entity.Land;
import com.selapak.selapakapi.model.entity.LandPhoto;
import com.selapak.selapakapi.model.request.LandPhotoRequest;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandPhotoResponse;
import com.selapak.selapakapi.repository.LandPhotoRepository;
import com.selapak.selapakapi.service.LandPhotoService;
import com.selapak.selapakapi.service.LandService;
import com.selapak.selapakapi.service.UploadImageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class LandPhotoServiceImpl implements LandPhotoService {
    public LandPhotoServiceImpl(LandPhotoRepository landPhotoRepository, @Lazy LandService landService, UploadImageService uploadImageService) {
        this.landPhotoRepository = landPhotoRepository;
        this.landService = landService;
        this.uploadImageService = uploadImageService;
    }

    private final LandPhotoRepository landPhotoRepository;
    private final LandService landService;
    private final UploadImageService uploadImageService;

    @Override
    public List<LandPhotoResponse> create(Land land, LandRequest landPhotoRequest) {
        Land landId = landService.getById(land.getId());

        List<LandPhotoResponse> landPhotoList = new ArrayList<>();
        for(MultipartFile file : landPhotoRequest.getLandPhotos()){

            if(file.isEmpty()){
                throw new ApplicationException("Data bad request", "Foto land tidak boleh kosong", HttpStatus.BAD_REQUEST);
            }

            String imageURL = uploadImageService.uploadImageLand(file);
            LandPhoto landPhoto = LandPhoto.builder()
                    .land(landId)
                    .imageURL(imageURL)
                    .isActive(true)
                    .build();
            landPhotoRepository.save(landPhoto);

            landPhotoList.add(LandPhotoResponse.builder()
                    .id(landPhoto.getId())
                    .imageURL(landPhoto.getImageURL())
                    .isActive(landPhoto.getIsActive())
                    .build());
        }

        return landPhotoList;
    }

    @Override
    public List<LandPhotoResponse> addPhoto(LandPhotoRequest landPhotoRequest) {
        Land landId = landService.getById(landPhotoRequest.getLandId());

        List<LandPhotoResponse> landPhotoList = new ArrayList<>();
        for(MultipartFile file : landPhotoRequest.getMultipartFile()){
            String imageURL = uploadImageService.uploadImageLand(file);
            LandPhoto landPhoto = LandPhoto.builder()
                    .land(landId)
                    .imageURL(imageURL)
                    .isActive(true)
                    .build();
            landPhotoRepository.save(landPhoto);

            landPhotoList.add(LandPhotoResponse.builder()
                    .id(landPhoto.getId())
                    .imageURL(landPhoto.getImageURL())
                    .isActive(landPhoto.getIsActive())
                    .build());
        }

        return landPhotoList;
    }

    @Override
    public List<LandPhotoResponse> getAllPhoto(String landId) {
        Land getLandId = landService.getById(landId);

        List<LandPhotoResponse> landPhotoResponses = getLandId.getLandPhotos().stream()
                .filter(images -> images.getIsActive())
                .map(landPhotoResponse -> LandPhotoResponse.builder()
                        .id(landPhotoResponse.getId())
                        .imageURL(landPhotoResponse.getImageURL())
                        .isActive(landPhotoResponse.getIsActive())
                        .build())
                .toList();
        return landPhotoResponses;
    }
}
