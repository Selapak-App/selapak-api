package com.selapak.selapakapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.request.RentPeriodRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.RentPeriodResponse;
import com.selapak.selapakapi.service.RentPeriodService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.RENT_PERIOD_PATH)
public class RentPeriodController {

    private final RentPeriodService rentPeriodService;

    @GetMapping
    public ResponseEntity<?> getAllRentPeriods() {
        List<RentPeriodResponse> rentPeriodResponse = rentPeriodService.getAllWithDto();
        CommonResponse<List<RentPeriodResponse>> response = CommonResponse.<List<RentPeriodResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all rent periods successfully.")
                .data(rentPeriodResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getRentPeriodById(@PathVariable String id) {
        RentPeriodResponse rentPeriodResponse = rentPeriodService.getByIdWithDto(id);
        CommonResponse<RentPeriodResponse> response = CommonResponse.<RentPeriodResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get rent period successfully.")
                .data(rentPeriodResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<?> createRentPeriod(@Valid @RequestBody RentPeriodRequest request) {
        RentPeriodResponse rentPeriodResponse = rentPeriodService.create(request);
        CommonResponse<RentPeriodResponse> response = CommonResponse.<RentPeriodResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create rent period successfully.")
                .data(rentPeriodResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
