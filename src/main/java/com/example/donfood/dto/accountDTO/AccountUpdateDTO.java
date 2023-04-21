package com.example.donfood.dto.accountDTO;

import com.example.donfood.model.enums.Right;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDTO {

    private String passwordDecoded;

    @NotEmpty
    private String fullName;

    private Right accessRights;

    private Boolean accountVerified;
}
