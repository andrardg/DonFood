package com.example.donfood.dto.orderDTO;

import com.example.donfood.model.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderUpdateDTO {

    private Double quantitySelected;

    private Status status;
}
