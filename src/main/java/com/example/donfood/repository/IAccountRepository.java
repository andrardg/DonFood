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
    Account findByEmail(String email);
    //@Modifying
    //@Query(value = "delete from Account a where a.email = :email2")
    //void deleteByEmail(@Param("email2") String email2);
    @Transactional
    void deleteByEmail(String email);
    List<Account> findByFullName(String fullName);
}
