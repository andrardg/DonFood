package com.example.donfood.dto.restaurantDTO;

import com.example.donfood.model.Account;
import com.example.donfood.model.Donation;
import com.example.donfood.model.ONG;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {

    @NotNull
    private Integer restaurantId;

    @NotNull
    private Account account;

    private String fiscalIdCode;

    private Double socialScore;

    private List<ONG> favOngs;

    private List<Donation> donations;
}
