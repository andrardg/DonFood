package com.example.donfood.repository;

import com.example.donfood.model.Account;
import com.example.donfood.model.ONG;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IONGRepository extends JpaRepository<ONG, Integer> {
    List<ONG> findByAccountONGFullName(String fullName);
    ONG findByOngId(Integer id);
    boolean existsByAccountONG(Account account);
    @Transactional
    void deleteByAccountONG(Account account);
}
