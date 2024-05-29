package com.selapak.selapakapi.controller;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.response.*;
import com.selapak.selapakapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ADMIN_PATH)
public class AdminContoller {
    private final AdminService adminService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> getAllAdmin(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size) {
        Page<AllAdminResponse> adminResponses = adminService.getAll(page - 1, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(adminResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<AllAdminResponse>> response = CommonResponseWithPage.<Page<AllAdminResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all admin successfully.")
                .data(adminResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.DEACTIVE_ADMIN)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> deactiveAdmin(@PathVariable String adminId) {
        AllAdminResponse adminResponse = adminService.deactiveAdmin(adminId);
        CommonResponse<AllAdminResponse> response = CommonResponse.<AllAdminResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Deactive admin successfully.")
                .data(adminResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.ACTIVE_ADMIN)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> activeAdmin(@PathVariable String adminId) {
        AllAdminResponse adminResponse = adminService.activeAdmin(adminId);
        CommonResponse<AllAdminResponse> response = CommonResponse.<AllAdminResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Active admin successfully.")
                .data(adminResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
