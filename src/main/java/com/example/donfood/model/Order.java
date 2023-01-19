package com.example.donfood.model;
import com.example.donfood.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.*;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    //@NotNull
    //@Column(name = "donationId")
    //private Long donationId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    //@NotNull
    //@Column(name = "ongId")
    //private Long ongId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ong_id", nullable = false)
    private ONG ong;

    @Min(value = 0, message = "quantity cannot be below 0")
    @Column(nullable = false)
    private Double quantitySelected;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Timestamp createdAt;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Feedback feedback;

}
