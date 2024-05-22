package com.selapak.selapakapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LandPhotoResponse {

    private String id;
    private String imageURL;
    private Boolean isActive;
    
}
