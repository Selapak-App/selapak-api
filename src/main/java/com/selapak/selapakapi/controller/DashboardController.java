package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.entity.BusinessType;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.DashboardResponse;
import com.selapak.selapakapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(AppPath.ADMIN_PATH)
public class DashboardController {

    private final DashboardService dashboardService;
    @GetMapping(AppPath.DASHBOARD)
    public ResponseEntity<?> getAllData() {
        DashboardResponse dashboardResponse = dashboardService.dashboardData();
        CommonResponse<DashboardResponse> response = CommonResponse.<DashboardResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all data successfully.")
                .data(dashboardResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
