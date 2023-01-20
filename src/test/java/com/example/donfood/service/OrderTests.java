package com.example.donfood.service;

import com.example.donfood.dto.orderDTO.OrderRequestDTO;
import com.example.donfood.dto.orderDTO.OrderUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.model.Donation;
import com.example.donfood.model.Order;
import com.example.donfood.model.enums.Status;
import com.example.donfood.repository.IDonationRepository;
import com.example.donfood.repository.IONGRepository;
import com.example.donfood.repository.IOrderRepository;
import com.example.donfood.service.orderService.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IONGRepository ongRepository;

    @Mock
    private IDonationRepository donationRepository;

    @Test
    public void createHappyFlow(){
        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(3.0)
                .build();

        Order dbOrder = Order.builder()
                .orderId(1)
                .donation(donation)
                .quantitySelected(1.0)
                .build();

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .donationId(1)
                .ongId(1)
                .orderId(1)
                .quantitySelected(2.0)
                .build();
        when(ongRepository.existsById(1)).thenReturn(true);
        when(donationRepository.existsById(1)).thenReturn(true);
        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        when(orderRepository.findAll()).thenReturn(List.of(dbOrder));
        assertNotNull(orderService.create(orderRequestDTO));
    }

    @Test
    public void createOngIdNotFound(){
        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(3.0)
                .build();
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .donationId(1)
                .ongId(1)
                .orderId(1)
                .quantitySelected(2.0)
                .build();

        when(ongRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> orderService.create(orderRequestDTO));
    }

    @Test
    public void createDonationIdNotFound(){
        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(3.0)
                .build();
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .donationId(1)
                .ongId(1)
                .orderId(1)
                .quantitySelected(2.0)
                .build();

        when(ongRepository.existsById(1)).thenReturn(true);
        when(donationRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> orderService.create(orderRequestDTO));
    }

    @Test
    public void createQuantityExceeded(){
        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(3.0)
                .build();

        Order dbOrder = Order.builder()
                .orderId(1)
                .donation(donation)
                .quantitySelected(1.0)
                .build();

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .donationId(1)
                .ongId(1)
                .orderId(1)
                .quantitySelected(3.0)
                .build();
        when(ongRepository.existsById(1)).thenReturn(true);
        when(donationRepository.existsById(1)).thenReturn(true);
        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        when(orderRepository.findAll()).thenReturn(List.of(dbOrder));
        assertThrows(IllegalArgumentException.class, ()-> orderService.create(orderRequestDTO));
    }

    @Test
    public void getAllHappyFlow(){
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        orderService.getAll();
    }

    @Test
    public void getByIdHappyFlow(){
        when(orderRepository.existsById(1)).thenReturn(true);
        Order order = Order.builder()
                .orderId(1)
                .quantitySelected(1.0)
                .build();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        assertNotNull(orderService.getById(1));
    }

    @Test
    public void getByIdNotFound(){
        when(orderRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()->orderService.getById(1));
    }

    @Test
    public void updateHappyFlow(){
        OrderUpdateDTO orderUpdateDTO = OrderUpdateDTO.builder()
                .quantitySelected(2.9)
                .status(Status.COMPLETED)
                .build();

        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(30.0)
                .build();

        Order dbOrder = Order.builder()
                .orderId(1)
                .donation(donation)
                .quantitySelected(1.0)
                .build();

        Order dbOrder2 = Order.builder()
                .orderId(1)
                .orderId(2)
                .donation(donation)
                .quantitySelected(1.0)
                .build();

        when(orderRepository.existsById(1)).thenReturn(true);
        when(orderRepository.findById(1)).thenReturn(Optional.of(dbOrder));
        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        when(orderRepository.findAll()).thenReturn(List.of(dbOrder, dbOrder2));

        assertNotNull(orderService.update(1, orderUpdateDTO));
    }

    @Test
    public void updateOrderIdNotFound(){
        when(orderRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()->orderService.update(1, any()));
    }

    @Test
    public void updateDonationIdNotFound(){
        OrderUpdateDTO orderUpdateDTO = OrderUpdateDTO.builder()
                .quantitySelected(2.9)
                .status(Status.COMPLETED)
                .build();

        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(3.0)
                .build();

        Order dbOrder = Order.builder()
                .orderId(1)
                .orderId(1)
                .donation(donation)
                .quantitySelected(1.0)
                .build();

        when(orderRepository.existsById(1)).thenReturn(true);
        when(orderRepository.findById(1)).thenReturn(Optional.of(dbOrder));
        when(donationRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()->orderService.update(1, orderUpdateDTO));
    }

    @Test
    public void updateQuantityExceeds(){
        OrderUpdateDTO orderUpdateDTO = OrderUpdateDTO.builder()
                .quantitySelected(6.0)
                .status(Status.COMPLETED)
                .build();

        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(5.0)
                .build();

        Order dbOrder = Order.builder()
                .orderId(1)
                .donation(donation)
                .quantitySelected(5.0)
                .build();
        when(orderRepository.existsById(1)).thenReturn(true);
        when(orderRepository.findById(1)).thenReturn(Optional.of(dbOrder));
        when(donationRepository.findById(1)).thenReturn(Optional.of(donation));
        when(orderRepository.findAll()).thenReturn(List.of(dbOrder));

        assertThrows(IllegalArgumentException.class, ()->orderService.update(1, orderUpdateDTO));
    }

    @Test
    public void updateHappyFlowSmallerQuantity(){
        OrderUpdateDTO orderUpdateDTO = OrderUpdateDTO.builder()
                .quantitySelected(4.0)
                .status(Status.COMPLETED)
                .build();

        Donation donation = Donation.builder()
                .donationId(1)
                .quantity(5.0)
                .build();

        Order dbOrder = Order.builder()
                .orderId(1)
                .donation(donation)
                .quantitySelected(5.0)
                .build();
        when(orderRepository.existsById(1)).thenReturn(true);
        when(orderRepository.findById(1)).thenReturn(Optional.of(dbOrder));

        assertNotNull(orderService.update(1, orderUpdateDTO));
    }

    @Test
    public void deleteHappyFlow(){
        when(orderRepository.existsById(1)).thenReturn(true);
        orderService.delete(1);
    }

    @Test
    public void deleteIdNull(){
        assertThrows(IllegalArgumentException.class, ()-> orderService.delete(null));
    }

    @Test
    public void deleteIdNotFound(){
        when(orderRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> orderService.delete(1));
    }
}
