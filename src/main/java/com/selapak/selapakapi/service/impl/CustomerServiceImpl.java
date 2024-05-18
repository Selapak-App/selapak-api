package com.selapak.selapakapi.service.impl;

import org.springframework.stereotype.Service;

import com.selapak.exception.CustomerNotFoundException;
import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.repository.CustomerRepository;
import com.selapak.selapakapi.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException());
    }

}
