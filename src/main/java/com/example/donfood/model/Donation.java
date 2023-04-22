package com.example.donfood.model;

import com.example.donfood.model.enums.Measure;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    //@NotEmpty
    //@Column(name = "restaurantId")
    //private Long restaurantId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @NotNull
    private Date expirationDate;

    @Column(nullable = false)
    @DecimalMin(value = "0.1", message = "the quantity cannot be below 0")
    @NotNull
    private Double quantity;

    @Column(nullable = false)
    @NotNull
    private Measure quantityMeasure;

    @Column(nullable = false)
    @NotEmpty
    private String product;

    @Column(nullable = false)
    @NotEmpty
    private String pickUpLocation;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    @NotNull
    private Date pickUpTime;

    private String photo;

    @Column(nullable = false)
    @NotNull
    private Timestamp createdAt;

    @Column(nullable = false)
    @NotNull
    private Timestamp modifiedAt;



    // one to many with order
    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
