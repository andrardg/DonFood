package com.example.donfood.dto.ongDTO;

import com.example.donfood.model.Account;
import com.example.donfood.model.Order;
import com.example.donfood.model.Restaurant;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ONGResponseDTO {

    @NotNull
    private Integer ongId;

    @NotNull
    private Account accountONG;

    @NotNull
    private String address;

    @NotNull
    private Integer nrPeopleHelping;

    private List<Restaurant> favRestaurants;

    private List<Order> orders;
}
