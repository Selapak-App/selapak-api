package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.CommonResponseWithPage;
import com.selapak.selapakapi.model.response.LandResponse;
import com.selapak.selapakapi.model.response.PagingResponse;
import com.selapak.selapakapi.service.LandService;
import com.selapak.selapakapi.service.UploadImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.LAND_PATH)
public class LandController {
    private final LandService landService;

    @PostMapping
    public ResponseEntity<?> createLand(@ModelAttribute LandRequest request) {
        LandResponse landResponse = landService.create(request);
        CommonResponse<LandResponse> response = CommonResponse.<LandResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create land owner successfully.")
                .data(landResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllLands(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<LandResponse> landResponses = landService.getAll(page - 1, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(landResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<LandResponse>> response = CommonResponseWithPage.<Page<LandResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all lands successfully.")
                .data(landResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getLandById(@PathVariable String id) {
        LandResponse landResponse = landService.getByIdWithDto(id);
        CommonResponse<LandResponse> response = CommonResponse.<LandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get land successfully.")
                .data(landResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.UPDATE_BY_ID)
    public ResponseEntity<?> updateLandById(@PathVariable String id, @RequestBody LandRequest request) {
        LandResponse landResponse = landService.updateById(id, request);
        CommonResponse<LandResponse> response = CommonResponse.<LandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Update land successfully.")
                .data(landResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public ResponseEntity<?> deleteLandById(@PathVariable String id) {
        landService.deleteById(id);
        CommonResponse<LandResponse> response = CommonResponse.<LandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Delete land successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
