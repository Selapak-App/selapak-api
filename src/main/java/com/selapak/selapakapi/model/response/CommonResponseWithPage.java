package com.selapak.selapakapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CommonResponseWithPage<T> {

    private Integer statusCode;
    private String message;
    private T data;
    private PagingResponse paging;
    
}
