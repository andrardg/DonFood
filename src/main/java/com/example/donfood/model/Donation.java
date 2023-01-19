package com.example.donfood.model;

import com.example.donfood.model.enums.Measure;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import jakarta.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer donationId;

    //@NotNull
    //@Column(name = "restaurantId")
    //private Long restaurantId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private Timestamp expirationDate;

    @Column(nullable = false)
    @DecimalMin(value = "0.1", message = "the quantity cannot be below 0")
    private Double quantity;

    @Column(nullable = false)
    private Measure quantityMeasure;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private String pickUpLocation;

    @Column(nullable = false)
    private Time pickUpTime;

    private String photo;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp modifiedAt;



    // one to many with order
    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
