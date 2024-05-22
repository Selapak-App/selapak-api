package com.selapak.selapakapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LandRequest {
    private String landOwnerId;
    private String address;
    private String district;
    private String village;
    private String postalCode;
    private String description;
    private String slotArea;
    private Integer slotAvailable;
    private Integer totalSlot;
    private Boolean isActive;
    private Long price;
    private List<MultipartFile> landPhotos;
    private List<BusinessTypeRequest> businessTypes;
}
