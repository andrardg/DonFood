package com.example.donfood.dto.accountDTO;

import com.example.donfood.model.enums.Right;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountUpdateDTO {

    private String passwordDecoded;

    private String fullName;

    private Right accessRights;

    private Boolean accountVerified;
}
