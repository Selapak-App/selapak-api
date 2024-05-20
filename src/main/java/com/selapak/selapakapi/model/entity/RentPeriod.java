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
@Table(name = DbTableSchema.RENT_PERIOD_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class RentPeriod {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private Integer period;
    @OneToMany(mappedBy = "rentPeriod")
    @JsonBackReference
    private List<Transaction> transactionList;
}
