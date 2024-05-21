package com.selapak.selapakapi.model.entity;

import com.selapak.selapakapi.constant.DbTableSchema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DbTableSchema.BUSINESS_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String businessName;

    private String description;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private BusinessType businessType;
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer customer;
}
