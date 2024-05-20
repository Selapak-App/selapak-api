package com.selapak.selapakapi.model.entity;

import com.selapak.selapakapi.constant.DbTableSchema;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = DbTableSchema.LAND_PRICE_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class LandPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long price;

    private Boolean isActive;
    
}
