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

    @OneToMany(mappedBy = "landOwner")
    @JsonBackReference
    private List<Land> lands;
}
