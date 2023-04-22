package com.example.donfood.dto.ongDTO;

import com.example.donfood.dto.accountDTO.AccountUpdateDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ONGUpdateDTO {

    private Integer ongId;

    private AccountUpdateDTO accountUpdateDTO;

    private String address;

    private Integer nrPeopleHelping;
}
