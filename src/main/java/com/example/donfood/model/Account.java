package com.example.donfood.model;
import com.example.donfood.model.enums.Right;
import jakarta.persistence.*;
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
    private String passwordEncoded;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Right accessRights;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Boolean accountVerified;
}
