package com.example.donfood.repository;

import com.example.donfood.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDonationRepository extends JpaRepository<Donation, Integer> {
    List<Donation> findByProduct(String product);
    List<Donation> getByQuantityAndProduct(Double quantity, String product);
}
