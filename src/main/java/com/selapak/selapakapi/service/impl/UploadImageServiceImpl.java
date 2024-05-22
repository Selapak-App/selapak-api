package com.selapak.selapakapi.service.impl;

import com.selapak.selapakapi.service.UploadImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadImageServiceImpl implements UploadImageService {

    private final S3Client s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;
    @Override
    public String uploadImageLand(MultipartFile multipartFile) {
        try {
            String fileName = "land-images/" + System.currentTimeMillis() + "-" + multipartFile.getOriginalFilename();
            s3Client.putObject(builder -> builder.bucket(bucketName).key(fileName).build(),
                    RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadImageUmkn(MultipartFile multipartFile) {
        try {
            String fileName = "umkm-images/" + System.currentTimeMillis() + "-" + multipartFile.getOriginalFilename();
            s3Client.putObject(builder -> builder.bucket(bucketName).key(fileName).build(),
                    RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
