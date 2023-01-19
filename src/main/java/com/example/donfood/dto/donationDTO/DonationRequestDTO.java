package com.example.donfood.dto.donationDTO;
import com.example.donfood.model.enums.Measure;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class DonationRequestDTO {

    private Integer donationId;

    @NotNull
    private Integer restaurantId;

    @NotNull
    private Timestamp expirationDate;

    @NotNull
    private Double quantity;

    @NotNull
    private Measure quantityMeasure;

    @NotNull
    private String product;

    @NotNull
    private String pickUpLocation;

    @NotNull
    private Time pickUpTime;

    private String photo;

    private Timestamp createdAt;

    private Timestamp modifiedAt;
}
