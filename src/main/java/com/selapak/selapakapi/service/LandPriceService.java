package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.LandPrice;

public interface LandPriceService {
    
    LandPrice getById(String id);

    LandPrice create(LandPrice landPrice);

}
