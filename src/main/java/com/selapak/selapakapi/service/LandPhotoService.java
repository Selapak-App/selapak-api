package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.Land;
import com.selapak.selapakapi.model.entity.LandPhoto;
import com.selapak.selapakapi.model.request.LandPhotoRequest;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandPhotoResponse;

import java.util.List;

public interface LandPhotoService {
    List<LandPhotoResponse> create (Land land, LandRequest landPhotoRequest);

    List<LandPhotoResponse> addPhoto(LandPhotoRequest landPhotoRequest);

    List<LandPhotoResponse> getAllPhoto (String landId);
}
