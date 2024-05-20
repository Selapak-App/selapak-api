package com.selapak.selapakapi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.selapak.selapakapi.constant.DbTableSchema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = DbTableSchema.LAND_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String address;
    private String district;
    private String village;
    private String postalCode;
    private String description;
    private String slotArea;
    private Integer slotAvailable;
    private Integer totalSlot;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "land_owner_id")
    private LandOwner landOwner;
    @OneToMany(mappedBy = "land")
    @JsonBackReference
    private List<LandPrice> landPriceList;
    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<BusinessRecomendation> businessRecomendations;
    @OneToMany(mappedBy = "land")
    @JsonBackReference
    private List<LandPhoto> landPhotos;
}
