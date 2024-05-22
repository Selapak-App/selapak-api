package com.selapak.selapakapi.repository;

import com.selapak.selapakapi.model.entity.Land;
import com.selapak.selapakapi.model.entity.LandPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LandPhotoRepository extends JpaRepository<LandPhoto, String> {
}
