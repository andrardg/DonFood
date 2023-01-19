package com.example.donfood.repository;

import com.example.donfood.model.Account;
import com.example.donfood.model.ONG;
import com.example.donfood.model.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRestaurantRepository  extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByAccountRestFullName(String fullName);

    Restaurant findByRestaurantId(Integer id);

    boolean existsByAccountRest(Account account);

    @Transactional
    void deleteByAccountRest(Account account);
}
