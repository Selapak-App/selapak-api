package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.Land;
import com.selapak.selapakapi.model.request.LandRequest;
import com.selapak.selapakapi.model.response.LandResponse;

public interface LandService {
    LandResponse create (LandRequest landRequest);
}
