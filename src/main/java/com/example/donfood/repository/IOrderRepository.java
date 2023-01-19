package com.example.donfood.repository;

import com.example.donfood.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Integer> {

}
