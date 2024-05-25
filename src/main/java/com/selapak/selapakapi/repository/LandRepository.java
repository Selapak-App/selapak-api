package com.selapak.selapakapi.repository;

import com.selapak.selapakapi.model.entity.Land;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandRepository extends JpaRepository<Land, String> {
    <T> List<Land> findAll(Specification<T> spec);
}
