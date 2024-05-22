package com.selapak.selapakapi.model.entity;

import com.selapak.selapakapi.constant.DbTableSchema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DbTableSchema.LAND_PHOTO_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class LandPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name= "image_url")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "land_id")
    private Land land;

    private Boolean isActive;
}
