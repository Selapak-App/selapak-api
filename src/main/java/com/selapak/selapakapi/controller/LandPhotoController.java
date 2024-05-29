package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.request.LandPhotoRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.LandPhotoResponse;
import com.selapak.selapakapi.service.LandPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.LAND_PHOTO_PATH)
public class LandPhotoController {
    private final LandPhotoService landPhotoService;
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> addPhoto(@ModelAttribute LandPhotoRequest landPhotoRequest) {
        List<LandPhotoResponse> landPhotoResponses = landPhotoService.addPhoto(landPhotoRequest);
        CommonResponse<List<LandPhotoResponse>> response = CommonResponse.<List<LandPhotoResponse>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Upload File successfully.")
                .data(landPhotoResponses)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
