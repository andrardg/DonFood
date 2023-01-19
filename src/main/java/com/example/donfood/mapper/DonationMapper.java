package com.example.donfood.mapper;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.model.Donation;
import org.modelmapper.ModelMapper;

public class DonationMapper {
    public static Donation requestToDonation(DonationRequestDTO donationRequestDTO){
        ModelMapper modelMapper = new ModelMapper();
        Donation donation = modelMapper.map(donationRequestDTO, Donation.class);
        return donation;
    }
}
