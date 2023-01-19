package com.example.donfood.dto.ongDTO;

import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ONGUpdateDTO {

    private Integer ongId;

    private AccountUpdateDTO accountUpdateDTO;

    private String address;

    private Integer nrPeopleHelping;
}
