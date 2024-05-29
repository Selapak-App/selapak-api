package com.selapak.selapakapi.repository;

import com.selapak.selapakapi.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selapak.selapakapi.model.entity.LandOwner;

import java.util.Optional;

@Repository
public interface LandOwnerRepository extends JpaRepository<LandOwner, String> {
    Optional<LandOwner> findByEmail(String email);
    Optional<LandOwner> findByPhoneNumber(String phone);

    Optional<LandOwner> findByNik(String nik);
}
