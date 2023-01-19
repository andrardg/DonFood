package com.example.donfood.dto.donationDTO;

import com.example.donfood.model.enums.Measure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class DonationUpdateDTO {
    private Integer restaurantId;

    private Timestamp expirationDate;

    private Double quantity;

    private Measure quantityMeasure;

    private String product;

    private String pickUpLocation;

    private Time pickUpTime;

    private String photo;
}
