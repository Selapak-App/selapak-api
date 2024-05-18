package com.selapak.selapakapi.service;

import com.selapak.selapakapi.model.entity.Customer;

public interface CustomerService {

    Customer create(Customer customer);

    Customer getById(String id);
    
}
