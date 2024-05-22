package com.selapak.selapakapi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.selapak.selapakapi.constant.DbTableSchema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = DbTableSchema.BUSINESS_TYPE_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class BusinessType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "businessType")
    @JsonBackReference
    private List<BusinessRecomendation> businessRecomendations;

    @OneToMany(mappedBy = "businessType")
    @JsonBackReference
    private List<Business> businessList;
    
}
