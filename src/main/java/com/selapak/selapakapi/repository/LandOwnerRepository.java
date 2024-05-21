package com.selapak.selapakapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selapak.selapakapi.model.entity.LandOwner;

@Repository
public interface LandOwnerRepository extends JpaRepository<LandOwner, String> {
    
}
