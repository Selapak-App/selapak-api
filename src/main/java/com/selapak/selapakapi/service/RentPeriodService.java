package com.selapak.selapakapi.service;

import java.util.List;

import com.selapak.selapakapi.model.entity.RentPeriod;
import com.selapak.selapakapi.model.request.RentPeriodRequest;
import com.selapak.selapakapi.model.response.RentPeriodResponse;

public interface RentPeriodService {
    
    RentPeriod getById(String id);

    List<RentPeriodResponse> getAllWithDto();

    RentPeriodResponse getByIdWithDto(String id);

    RentPeriodResponse create(RentPeriodRequest request);

}
