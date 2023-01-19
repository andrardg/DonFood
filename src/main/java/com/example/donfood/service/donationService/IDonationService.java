package com.example.donfood.service.donationService;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.dto.donationDTO.DonationUpdateDTO;
import com.example.donfood.model.Donation;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IDonationService {
    Donation create(DonationRequestDTO donationRequestDTO);
    List<Donation> getAll();
    List<Donation> findByProduct(String product);
    Donation getById(Integer id);
    List<Donation> getByQuantityAndProduct(Double quantity, String product);
    Donation update(Integer id, DonationUpdateDTO donationUpdateDTO);
    void delete(Integer id);
}
