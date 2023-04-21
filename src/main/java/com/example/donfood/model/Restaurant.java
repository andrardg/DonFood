package com.example.donfood.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="email", nullable = false)
    @NotNull
    private Account accountRest;

    private String fiscalIdCode;

    @Max(value = 5, message = "social score cannot be greater than 5")
    @Min(value = 0, message = "social score cannot be below 0")
    private Double socialScore;



    //one to many with donation
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Donation> donations = new ArrayList<>();

    //ongs add restaurants to favorites
    @ManyToMany(fetch=FetchType.LAZY,  cascade = CascadeType.ALL, mappedBy = "favRestaurants")
    private List<ONG> favOngs = new ArrayList<>();
}
