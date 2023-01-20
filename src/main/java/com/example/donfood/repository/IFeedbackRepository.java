package com.example.donfood.repository;

import com.example.donfood.model.Feedback;
import com.example.donfood.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IFeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByOrder(Order order);

    @Transactional
    @Modifying
    @Query(value = "delete from Feedback where feedback_id = :id", nativeQuery = true)
    void deleteViaId(@Param("id") Integer id);
}
