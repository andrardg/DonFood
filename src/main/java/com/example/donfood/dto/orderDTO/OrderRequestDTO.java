package com.example.donfood.dto.orderDTO;

import com.example.donfood.model.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class OrderRequestDTO {

    private Integer orderId;

    @NotNull
    private Integer donationId;

    @NotNull
    private Integer ongId;

    @NotNull
    private Double quantitySelected;

    private Status status;

    private Timestamp createdAt;
}
