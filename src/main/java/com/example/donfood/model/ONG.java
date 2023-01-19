package com.example.donfood.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ONG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ongId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="email", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Account accountONG;

    @Column(nullable = false)
    private String address;

    @Min(value = 0, message = "the nr of people the ong helps cannot be below 0")
    @Column(nullable = false)
    private Integer nrPeopleHelping;



    //one to many with orders
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    //many to many with favorite restaurants
    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "favorite",
            joinColumns = {@JoinColumn (name = "ong_id")},
            inverseJoinColumns = {@JoinColumn (name = "restaurant_id")})
    private List<Restaurant> favRestaurants = new ArrayList<>();

}
