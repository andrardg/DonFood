package com.example.donfood.controller;

import com.example.donfood.dto.donationDTO.DonationRequestDTO;
import com.example.donfood.dto.donationDTO.DonationUpdateDTO;
import com.example.donfood.dto.restaurantDTO.RestaurantResponseDTO;
import com.example.donfood.model.Donation;
import com.example.donfood.model.enums.Measure;
import com.example.donfood.service.donationService.DonationService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("h2")
public class DonationControllerTests {

    @InjectMocks
    private DonationController donationController;

    @Mock
    private DonationService donationService;

    @Test
    public void getAllHappyFlow(){
        Page<Donation> donationPage
                = new PageImpl<Donation>(Collections.emptyList(), PageRequest.of(1, 5), 5);

        when(donationService.getAll()).thenReturn(Collections.emptyList());
        when(donationService.findPaginated(PageRequest.of(0, 5))).thenReturn(donationPage);
        ModelAndView model = donationController.getAll(Optional.of(1), Optional.of(5));
        assertEquals(model.getModel().get("donationPage"), donationPage);
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

        ModelAndView model = donationController.getById(1);
        assertNotNull(model.getModel().get("donation"));
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
        Donation donationUpdateDTO = Donation.builder()
                .donationId(1)
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
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(donationService.update(id, donationUpdateDTO)).thenReturn(donation);
        ModelAndView model = donationController.update(id, donationUpdateDTO, result);

        assertNotNull(model.getModel().get("donation"));
    }

    @Test
    public void deleteHappyFlow() throws IOException {
        Integer id = 1;
        MockHttpServletResponse response = new MockHttpServletResponse();
        donationController.delete(id, response);
    }
}
