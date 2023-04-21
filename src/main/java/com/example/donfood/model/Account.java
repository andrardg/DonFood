package com.example.donfood.model;
import com.example.donfood.model.enums.Right;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.sql.Timestamp;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    private String email;

    @Column(name="password", nullable = false)
    @NotNull
    private String passwordEncoded;

    @Column(nullable = false)
    @NotNull
    private String fullName;

    @Column(nullable = false)
    @NotNull
    private Right accessRights;

    @Column(nullable = false)
    @NotNull
    private Timestamp createdAt;

    @Column(nullable = false)
    @NotNull
    private Boolean accountVerified;

}
