package com.selapak.selapakapi.model.entity;

import com.selapak.selapakapi.constant.DbTableSchema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DbTableSchema.BUSINESS_RECOMENDATION_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class BusinessRecomendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "land_id")
    private Land land;
    @ManyToOne
    @JoinColumn(name = "business_type_id")
    private BusinessType businessType;
}
