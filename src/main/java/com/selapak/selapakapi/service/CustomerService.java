package com.selapak.selapakapi.service;

import org.springframework.data.domain.Page;

import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.model.request.CustomerUpdateRequest;
import com.selapak.selapakapi.model.response.CustomerResponse;

public interface CustomerService {

    Customer create(Customer customer);

    Customer getById(String id);

    Page<CustomerResponse> getAllWithDto(Integer page, Integer size);

    CustomerResponse getByIdWithDto(String id);

    CustomerResponse getByEmailWithDto(String email);

    CustomerResponse updateById(String id, CustomerUpdateRequest request);

    void deleteById(String id);
    
}
