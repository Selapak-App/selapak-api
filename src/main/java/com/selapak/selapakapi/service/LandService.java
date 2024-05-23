package com.selapak.selapakapi.service;

import org.springframework.data.domain.Page;

import com.selapak.selapakapi.model.entity.Land;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandResponse;

import java.util.List;

public interface LandService {

    Land getById(String id);

    LandResponse create(LandRequest landRequest);

    LandResponse getByIdWithDto(String id);

    Page<LandResponse> getAll(Integer page, Integer size);
    List<Land> getAll();

    LandResponse updateById(String id, LandRequest request);

    void deleteById(String id);

    void decreaseLandSlotAvailable(String id, Integer quantity);

}
