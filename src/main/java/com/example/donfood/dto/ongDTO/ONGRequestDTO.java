package com.example.donfood.dto.ongDTO;
import com.example.donfood.dto.accountDTO.AccountRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ONGRequestDTO {

    private Integer ongId;

    @NotNull
    private AccountRequestDTO accountRequestDTO;

    @NotNull
    private String address;

    @NotNull
    private Integer nrPeopleHelping;
}
