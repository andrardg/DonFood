package com.example.donfood.service.donationService;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.dto.donationDTO.DonationUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.DonationMapper;
import com.example.donfood.model.Donation;
import com.example.donfood.repository.IDonationRepository;
import com.example.donfood.repository.IRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService implements IDonationService{

    @Autowired
    private IRestaurantRepository restaurantRepository;
    @Autowired
    private IDonationRepository donationRepository;

    @Override
    public Donation create(DonationRequestDTO donationRequestDTO) {
        if(!restaurantRepository.existsById(donationRequestDTO.getRestaurantId()))
            throw new ResourceNotFoundException("No Restaurant with that id.");

        donationRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        donationRequestDTO.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));

        Donation donation = DonationMapper.requestToDonation(donationRequestDTO);
        donationRepository.save(donation);
        return donation;
    }

    @Override
    public List<Donation> getAll() {
        return donationRepository.findAll();
    }

    @Override
    public List<Donation> findByProduct(String product) {
        return donationRepository.findByProduct(product);
    }

    @Override
    public Donation getById(Integer id) {

        if(!donationRepository.existsById(id))
            throw new ResourceNotFoundException("No Donation with that id.");

        Donation donation = donationRepository.findById(id).get();
        return donation;
    }

    @Override
    public List<Donation> getByQuantityAndProduct(Double quantity, String product) {
        return donationRepository.getByQuantityAndProduct(quantity, product);
    }

    @Override
    public Donation update(Integer id, DonationUpdateDTO donationUpdateDTO) {
        Optional<Donation> dbDonationOpt= donationRepository.findById(id);

        if(dbDonationOpt.isEmpty())
            throw new ResourceNotFoundException("Donation was not found by id");

        Donation dbDonation = dbDonationOpt.get();

        if(donationUpdateDTO.getExpirationDate() != null)
            dbDonation.setExpirationDate(donationUpdateDTO.getExpirationDate());

        if(donationUpdateDTO.getQuantity() != null)
            dbDonation.setQuantity(donationUpdateDTO.getQuantity());

        if(donationUpdateDTO.getQuantityMeasure() != null)
            dbDonation.setQuantityMeasure(donationUpdateDTO.getQuantityMeasure());

        if(donationUpdateDTO.getProduct() != null)
            dbDonation.setProduct(donationUpdateDTO.getProduct());

        if(donationUpdateDTO.getPickUpLocation() != null)
            dbDonation.setPickUpLocation(donationUpdateDTO.getPickUpLocation());

        if(donationUpdateDTO.getPickUpTime() != null)
            dbDonation.setPickUpTime(donationUpdateDTO.getPickUpTime());

        if(donationUpdateDTO.getPhoto() != null)
            dbDonation.setPhoto(donationUpdateDTO.getPhoto());

        dbDonation.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        donationRepository.save(dbDonation);
        return dbDonation;
    }
    @Override
    public Donation update(Integer id, Donation donation) {
        if(donationRepository.existsById(id) == false)
            throw new ResourceNotFoundException("Donation was not found by id");
        donation.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        donationRepository.save(donation);
        return donation;
    }
    @Override
    public void delete(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!donationRepository.existsById(id))
            throw new ResourceNotFoundException("The Donation with id " + id + " was not found");

        donationRepository.deleteById(id);
    }
}
