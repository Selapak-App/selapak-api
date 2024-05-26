package com.selapak.selapakapi.service;

import org.springframework.data.domain.Page;

import com.selapak.selapakapi.model.entity.LandOwner;
import com.selapak.selapakapi.model.request.LandOwnerRequest;
import com.selapak.selapakapi.model.response.LandOwnerResponse;

import java.util.List;

public interface LandOwnerService {
    
    LandOwner getById(String id);

    Page<LandOwnerResponse> getAllWithDto(Integer page, Integer size);
    List<LandOwner> getAll();

    LandOwnerResponse getByIdWithDto(String id);

    LandOwnerResponse create(LandOwnerRequest request);

    LandOwnerResponse updateById(String id, LandOwnerRequest request);

    void deleteById(String id);

}
