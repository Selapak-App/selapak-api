package com.selapak.selapakapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {

    String uploadImage(MultipartFile multipartFile);
}
