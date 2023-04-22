package com.example.donfood.service;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.dto.donationDTO.DonationUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.DonationMapper;
import com.example.donfood.model.Donation;
import com.example.donfood.model.enums.Measure;
import com.example.donfood.repository.IDonationRepository;
import com.example.donfood.repository.IRestaurantRepository;
import com.example.donfood.service.donationService.DonationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("h2")
public class DonationServiceTests {

    @InjectMocks
    private DonationService donationService;

    @Mock
    private Donation donation;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IDonationRepository donationRepository;

    @Test
    void createHappyFlow(){
        when(restaurantRepository.existsById(1)).thenReturn(true);
        DonationRequestDTO donationRequestDTO = DonationRequestDTO.builder()
                .donationId(1)
                .restaurantId(1)
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Donation donation = DonationMapper.requestToDonation(donationRequestDTO);
        when(donationRepository.save(any())).thenReturn(donation);

        assertEquals(donation.getProduct(), donationService.create(donationRequestDTO).getProduct());
    }

    @Test
    void createRestaurantNotFound(){
        DonationRequestDTO donationRequestDTO = DonationRequestDTO.builder()
                .donationId(1)
                .restaurantId(1)
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        assertThrows(ResourceNotFoundException.class, () -> donationService.create(donationRequestDTO));
    }

    @Test
    void getAllHappyFlow(){
        when(donationRepository.findAll()).thenReturn(Collections.emptyList());
        donationService.getAll();
    }

    @Test
    void findByProductHappyFlow(){
        when(donationRepository.findByProduct("cartof")).thenReturn(Collections.emptyList());
        donationService.findByProduct("cartof");
    }

    @Test
    void getByIdHappyFlow(){
        when(donationRepository.existsById(1)).thenReturn(true);
        Donation donation = Donation.builder().build();
        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        donationService.getById(1);
    }

    @Test
    void getByIdNotFound(){
        when(donationRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> donationService.getById(1));
    }

    @Test
    void getByQuantityAndProductHappyFlow(){
        when(donationRepository.getByQuantityAndProduct(1.0,"cartofi")).thenReturn(Collections.emptyList());
        donationService.getByQuantityAndProduct(1.0, "cartofi");
    }

    @Test
    void updateHappyFlow(){
        Donation donation = Donation.builder()
                .donationId(1)
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        DonationUpdateDTO donationUpdateDTO = DonationUpdateDTO.builder()
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .photo("104395740w3")
                .build();
        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        donationService.update(1, donationUpdateDTO);
    }

    @Test
    void updateIdNotFound(){
        Donation donation = Donation.builder()
                .donationId(1)
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        when(donationRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> donationService.update(1, donation));
    }

    @Test
    void deleteHappyFlow(){
        when(donationRepository.existsById(1)).thenReturn(true);
        donationService.delete(1);
    }

    @Test
    void deleteIdNotFound(){
        when(donationRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> donationService.delete(1));
    }

    @Test
    void deleteIdNull(){
        assertThrows(IllegalArgumentException.class, () -> donationService.delete(null));
    }
}