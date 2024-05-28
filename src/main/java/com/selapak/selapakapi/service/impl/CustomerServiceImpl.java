package com.selapak.selapakapi.service.impl;

import java.time.Instant;
import java.util.List;

import com.selapak.selapakapi.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selapak.selapakapi.exception.CustomerNotFoundException;
import com.selapak.selapakapi.model.entity.Customer;
import com.selapak.selapakapi.model.request.CustomerUpdateRequest;
import com.selapak.selapakapi.model.response.CustomerResponse;
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
        return customerRepository.findById(id).orElseThrow(() -> new ApplicationException("Data customer request not found", "Data pelanggan tidak ditemukan", HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<CustomerResponse> getAllWithDto(Integer page, Integer size) {
        Page<Customer> customers = customerRepository.findAll(PageRequest.of(page,size));

        return customers.map(this::convertToCustomerResponse);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerResponse getByIdWithDto(String id) {
        Customer customer = getById(id);

        return convertToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getByEmailWithDto(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new ApplicationException("Data customer request by email not found", "Data email pelanggan tidak ditemukan", HttpStatus.NOT_FOUND));

        return convertToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateById(String id, CustomerUpdateRequest request) {
        Customer existingCustomer = getById(id);

        existingCustomer = existingCustomer.toBuilder()
                .id(existingCustomer.getId())
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .nik(request.getNik())
                .address(request.getAddress())
                .updatedAt(Instant.now().toEpochMilli())
                .build();
        customerRepository.save(existingCustomer);

        return convertToCustomerResponse(existingCustomer);
    }

    @Override
    public void deleteById(String id) {
        Customer customer = getById(id);
        customer.setIsActive(false);
        customerRepository.save(customer);
    }

    private CustomerResponse convertToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .gender(customer.getGender().toString())
                .address(customer.getAddress())
                .nik(customer.getNik())
                .isActive(customer.getIsActive())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

}
