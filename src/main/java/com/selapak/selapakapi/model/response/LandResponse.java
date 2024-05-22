package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.model.entity.BusinessType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LandResponse {
    private String id;
    private LandOwnerResponse landOwner;
    private String address;
    private String district;
    private String village;
    private String postalCode;
    private String description;
    private String slotArea;
    private Integer slotAvailable;
    private Integer totalSlot;
    private Boolean isActive;
    private List<LandPhotoResponse> landPhotos;
    private LandPriceWithoutLandResponse landPrice;
    private List<BusinessType> businessTypes;
}