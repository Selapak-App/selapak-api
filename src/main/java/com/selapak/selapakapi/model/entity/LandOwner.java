package com.selapak.selapakapi.model.entity;

import com.selapak.selapakapi.constant.DbTableSchema;

import jakarta.persistence.Column;
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
@Table(name = DbTableSchema.LAND_OWNER_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class LandOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 15)
    private String phoneNumber;

    @Column(unique = true)
    private String nik;

    private Boolean isActive;

}
