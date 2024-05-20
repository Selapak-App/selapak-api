package com.selapak.selapakapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.RentPeriodNotFoundException;
import com.selapak.selapakapi.model.entity.RentPeriod;
import com.selapak.selapakapi.model.request.RentPeriodRequest;
import com.selapak.selapakapi.model.response.RentPeriodResponse;
import com.selapak.selapakapi.repository.RentPeriodRepository;
import com.selapak.selapakapi.service.RentPeriodService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentPeriodServiceImpl implements RentPeriodService {

    private final RentPeriodRepository rentPeriodRepository;

    @Override
    public RentPeriod getById(String id) {
        return rentPeriodRepository.findById(id).orElseThrow(() -> new RentPeriodNotFoundException());
    }

    @Override
    public List<RentPeriodResponse> getAllWithDto() {
        List<RentPeriod> rentPeriods = rentPeriodRepository.findAll();

        return rentPeriods.stream().map(this::convertToRentPeriodResponse).toList();
    }

    @Override
    public RentPeriodResponse getByIdWithDto(String id) {
        RentPeriod rentPeriod = getById(id);

        return convertToRentPeriodResponse(rentPeriod);
    }  

    @Override
    public RentPeriodResponse create(RentPeriodRequest request) {
        RentPeriod rentPeriod = RentPeriod.builder()
                .period(request.getPeriod())
                .build();
        rentPeriodRepository.saveAndFlush(rentPeriod);

        return convertToRentPeriodResponse(rentPeriod);
    }
   
    private RentPeriodResponse convertToRentPeriodResponse(RentPeriod rentPeriod) {
        return RentPeriodResponse.builder()
                .id(rentPeriod.getId())
                .period(rentPeriod.getPeriod())
                .build();
    }
    
}
