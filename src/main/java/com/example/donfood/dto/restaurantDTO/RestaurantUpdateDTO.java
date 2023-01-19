package com.example.donfood.dto.restaurantDTO;

import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantUpdateDTO {

    private Integer restaurantId;

    private AccountUpdateDTO accountUpdateDTO;

    private String fiscalIdCode;

    private Double socialScore;
}
