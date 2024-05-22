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

    @ManyToOne
    @JoinColumn(name = "land_id")
    @JsonBackReference
    private Land land;

    @OneToMany(mappedBy = "landPrice")
    @JsonBackReference
    private List<Transaction> transactionList;

}
