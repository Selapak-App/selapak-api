package com.selapak.selapakapi.repository;

import com.selapak.selapakapi.model.entity.LandPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandPhotoRepository extends JpaRepository<LandPhoto, String> {
}
