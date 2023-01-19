package com.example.donfood.dto.restaurantDTO;

import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantRequestDTO {

    private Integer restaurantId;

    @NotNull
    private AccountRequestDTO accountRequestDTO;

    private String fiscalIdCode;

    private Double socialScore;
}
