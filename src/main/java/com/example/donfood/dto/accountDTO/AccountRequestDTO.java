package com.example.donfood.dto.accountDTO;

import com.example.donfood.model.enums.Right;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class AccountRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String passwordDecoded;

    private String fullName;

    private Right accessRights;

    private Timestamp createdAt;

    private Boolean accountVerified;
}
