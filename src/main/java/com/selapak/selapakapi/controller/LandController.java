package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.request.LandOwnerRequest;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.LandOwnerResponse;
import com.selapak.selapakapi.model.response.LandResponse;
import com.selapak.selapakapi.service.LandService;
import com.selapak.selapakapi.service.UploadImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.LAND_PATH)
public class LandController {
    private final LandService landService;
    private final UploadImageService uploadImageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLand(@RequestBody LandRequest request) {
        LandResponse landResponse = landService.create(request);
        CommonResponse<LandResponse> response = CommonResponse.<LandResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create land owner successfully.")
                .data(landResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/image")
    public ResponseEntity<?> createLand(@RequestParam MultipartFile multipartFile) {
        String imageURL = uploadImageService.uploadImage(multipartFile);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Upload File successfully.")
                .data(imageURL)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
