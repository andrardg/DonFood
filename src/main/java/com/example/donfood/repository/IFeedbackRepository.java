package com.example.donfood.repository;

import com.example.donfood.model.Feedback;
import com.example.donfood.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByOrder(Order order);
}
