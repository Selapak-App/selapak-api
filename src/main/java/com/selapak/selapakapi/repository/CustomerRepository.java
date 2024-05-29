package com.selapak.selapakapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selapak.selapakapi.model.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumber(String phone);

    Optional<Customer> findByNik(String nik);
    
}
