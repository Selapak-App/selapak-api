package com.selapak.selapakapi.model.response;

import com.selapak.selapakapi.constant.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AllAdminResponse {
    private String id;
    private String name;
    private String email;
    private Boolean isActive;
    private AdminStatus status;
}
