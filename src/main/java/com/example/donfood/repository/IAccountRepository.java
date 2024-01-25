package com.example.donfood.repository;

import com.example.donfood.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, String> {
    boolean existsByEmail(String email);

    //@Query(value = "CALL DECRIPTACCOUNTFINDBYEMAIL(:email);", nativeQuery = true)
    @Query(value = "SELECT new com.example.donfood.model.Account(a.email, to_char(decriptare_parola(a.passwordEncoded)), a.fullName, a.accessRights, a.createdAt, a.accountVerified) \n" +
            "    FROM Account a \n" +
            "    where a.email = ?1")
    Account findByEmail(@Param("email") String email);

    @Transactional
    void deleteByEmail(String email);
    @Query(value = "SELECT new com.example.donfood.model.Account(a.email, to_char(decriptare_parola(a.passwordEncoded)), a.fullName, a.accessRights, a.createdAt, a.accountVerified) \n" +
            "    FROM Account a \n" +
            "            where a.fullName = ?1")
    List<Account> findByFullName(String fullName);
}
