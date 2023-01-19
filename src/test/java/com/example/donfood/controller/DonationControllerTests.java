package com.example.donfood.controller;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.dto.donationDTO.DonationUpdateDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.model.Donation;
import com.example.donfood.model.enums.Measure;
import com.example.donfood.service.donationService.DonationService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DonationControllerTests {

    @InjectMocks
    private DonationController donationController;

    @Mock
    private DonationService donationService;

    @Test
    public void getAllHappyFlow(){
        when(donationService.getAll()).thenReturn(Collections.emptyList());
        donationController.getAll();
    }

    @Test
    public void getByIdHappyFlow(){
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
        when(donationService.getById(1)).thenReturn(donation);

        ResponseEntity<Donation> response = donationController.getById(1);
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    public void getByProductHappyFlow(){
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
        List<Donation> list = new ArrayList<>();
        list.add(donation);
        when(donationService.findByProduct("a")).thenReturn(list);

        ResponseEntity<List<Donation>> response = donationController.getByProduct("a");
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    public void getByQuantityAndProductHappyFlow(){
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
        List<Donation> list = new ArrayList<>();
        list.add(donation);
        when(donationService.getByQuantityAndProduct(1.0, "a")).thenReturn(list);

        ResponseEntity<List<Donation>> response = donationController.getByQuantityAndProduct(1.0, "a");
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    public void createHappyFlow(){
        DonationRequestDTO donationRequestDTO = DonationRequestDTO.builder()
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .modifiedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

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

        when(donationService.create(donationRequestDTO)).thenReturn(donation);
        ResponseEntity<Donation> response = donationController.create(donationRequestDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
    }
    @Test
    public void updateHappyFlow(){
        Integer id = 1;
        DonationUpdateDTO donationUpdateDTO = DonationUpdateDTO.builder()
                .product("test")
                .quantity(1.0)
                .quantityMeasure(Measure.KG)
                .expirationDate(Timestamp.valueOf(LocalDateTime.now()))
                .pickUpLocation("1")
                .pickUpTime(new Time(0))
                .build();

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

        when(donationService.update(id, donationUpdateDTO)).thenReturn(donation);
        ResponseEntity<Donation> response = donationController.update(id, donationUpdateDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteHappyFlow(){
        Integer id = 1;
        donationController.delete(id);
    }
}
