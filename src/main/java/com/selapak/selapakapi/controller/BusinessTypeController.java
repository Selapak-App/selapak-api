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
import com.selapak.selapakapi.model.entity.BusinessType;
import com.selapak.selapakapi.model.request.BusinessTypeRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.service.BusinessTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.BUSINESS_TYPE_PATH)
public class BusinessTypeController {

    private final BusinessTypeService businessTypeService;

    @PostMapping
    public ResponseEntity<?> createBusinessType(@Valid @RequestBody BusinessTypeRequest request) {
        BusinessType businessTypeResponse = businessTypeService.createWithDto(request);
        CommonResponse<BusinessType> response = CommonResponse.<BusinessType>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create business type successfully.")
                .data(businessTypeResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllBusinessTypes() {
        List<BusinessType> businessTypes = businessTypeService.getAll();
        CommonResponse<List<BusinessType>> response = CommonResponse.<List<BusinessType>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all business types successfully.")
                .data(businessTypes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getBusinessTypeById(@PathVariable String id) {
        BusinessType businessType = businessTypeService.getById(id);
        CommonResponse<BusinessType> response = CommonResponse.<BusinessType>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get business type successfully.")
                .data(businessType)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
