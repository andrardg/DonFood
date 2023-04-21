package com.example.donfood.dto.restaurantDTO;

import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantUpdateDTO {

    private Integer restaurantId;
    @Valid
    private AccountUpdateDTO accountUpdateDTO;

    private String fiscalIdCode;

    private Double socialScore;
}
