package com.selapak.selapakapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.entity.LandPrice;
import com.selapak.selapakapi.model.request.LandPriceRequest;
import com.selapak.selapakapi.model.request.LandPriceUpdateRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.CommonResponseWithPage;
import com.selapak.selapakapi.model.response.LandPriceResponse;
import com.selapak.selapakapi.model.response.PagingResponse;
import com.selapak.selapakapi.service.LandPriceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.LAND_PRICE_PATH)
public class LandPriceController {

    private final LandPriceService landPriceService;

    @GetMapping(AppPath.GET_BY_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> getLandPriceById(@PathVariable String id) {
        LandPrice landPrice = landPriceService.getById(id);
        CommonResponse<LandPrice> response = CommonResponse.<LandPrice>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get land price successfully.")
                .data(landPrice)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> createLandPrice(@Valid @RequestBody LandPriceRequest request) {
        LandPriceResponse landPrice = landPriceService.createWithDto(request);
        CommonResponse<LandPriceResponse> response = CommonResponse.<LandPriceResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create land price successfully.")
                .data(landPrice)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> updateLandPriceById(@Valid @RequestBody LandPriceUpdateRequest request) {
        LandPriceResponse landPrice = landPriceService.updateById(request);
        CommonResponse<LandPriceResponse> response = CommonResponse.<LandPriceResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Update land price successfully.")
                .data(landPrice)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> getAllLandPrices(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<LandPriceResponse> landPriceResponses = landPriceService.getAll(page - 1, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(landPriceResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<LandPriceResponse>> response = CommonResponseWithPage.<Page<LandPriceResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all land prices successfully.")
                .data(landPriceResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
