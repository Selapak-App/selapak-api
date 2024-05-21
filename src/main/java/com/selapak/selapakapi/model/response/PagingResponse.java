package com.selapak.selapakapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class PagingResponse {

    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
    
}
