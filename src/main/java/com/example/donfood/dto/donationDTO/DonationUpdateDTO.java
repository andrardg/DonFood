package com.example.donfood.dto.donationDTO;

import com.example.donfood.model.enums.Measure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class DonationUpdateDTO {
    private Integer restaurantId;

    private Date expirationDate;

    private Double quantity;

    private Measure quantityMeasure;

    private String product;

    private String pickUpLocation;

    private Date pickUpTime;

    private String photo;
}
