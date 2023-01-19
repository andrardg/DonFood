package com.example.donfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;
import lombok.*;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false)
    private String comment;

    @Max(value = 5, message = "rating cannot be greater than 5")
    @Min(value = 1, message = "rating cannot be below 0")
    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private Timestamp createdAt;

}
