package com.selapak.selapakapi.model.entity;

import com.selapak.selapakapi.constant.DbTableSchema;
import com.selapak.selapakapi.constant.Payment;
import com.selapak.selapakapi.constant.Verify;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DbTableSchema.TRANSACTION_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long createdAt;
    private Long updatedAt;
    private Boolean isActive;
    
    @Column(name = "qty")
    private Integer quantity;

    private Boolean surveyStatus;
    
    @Enumerated(EnumType.STRING)
    private Verify verifyStatus;
    
    @Enumerated(EnumType.STRING)
    private Payment paymentStatus;
    
    @ManyToOne
    @JoinColumn(name = "verified_by")
    private Admin verifiedBy;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "rent_period_id")
    private RentPeriod rentPeriod;
    
    @ManyToOne
    @JoinColumn(name = "price_id")
    private LandPrice landPrice;
    
    @OneToOne
    private Business business;
}
