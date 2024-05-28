package com.selapak.selapakapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.request.LandOwnerRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.CommonResponseWithPage;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.model.response.PagingResponse;
import com.selapak.selapakapi.service.LandOwnerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.LAND_OWNER_PATH)
public class LandOwnerController {

    private final LandOwnerService landOwnerService;

    @PostMapping
    public ResponseEntity<?> createLandOwner(@Valid @RequestBody LandOwnerRequest request) {
        LandOwnerResponse landOwnerResponse = landOwnerService.create(request);
        CommonResponse<LandOwnerResponse> response = CommonResponse.<LandOwnerResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create land owner successfully.")
                .data(landOwnerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllLandOwners(@RequestParam(defaultValue = "1") Integer page, 
            @RequestParam(defaultValue = "10") Integer size) {
        Page<LandOwnerResponse> landOwnerResponses = landOwnerService.getAllWithDto(page - 1, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(landOwnerResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<LandOwnerResponse>> response = CommonResponseWithPage.<Page<LandOwnerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all land owners successfully.")
                .data(landOwnerResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getLandOwnerById(@PathVariable String id) {
        LandOwnerResponse landOwnerResponse = landOwnerService.getByIdWithDto(id);
        CommonResponse<LandOwnerResponse> response = CommonResponse.<LandOwnerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get land owner successfully.")
                .data(landOwnerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    public ResponseEntity<?> updateLandOwnerById(@PathVariable String id, @Valid @RequestBody LandOwnerRequest request) {
        LandOwnerResponse landOwnerResponse = landOwnerService.updateById(id, request);
        CommonResponse<LandOwnerResponse> response = CommonResponse.<LandOwnerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Update land owner successfully.")
                .data(landOwnerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public ResponseEntity<?> deleteLandOwnerById(@PathVariable String id) {
        landOwnerService.deleteById(id);
        CommonResponse<LandOwnerResponse> response = CommonResponse.<LandOwnerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Delete land owner successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
