package com.example.donfood.service;

import com.example.donfood.dto.feedbackDTO.FeedbackRequestDTO;
import com.example.donfood.dto.feedbackDTO.FeedbackUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.model.Feedback;
import com.example.donfood.model.Order;
import com.example.donfood.repository.IFeedbackRepository;
import com.example.donfood.service.feedbackService.FeedbackService;
import com.example.donfood.service.orderService.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTests {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private IFeedbackRepository feedbackRepository;

    @Mock
    private OrderService orderService;

    @Test
    public void createHappyFlow(){
        Order order = new Order();
        when(orderService.getById(1)).thenReturn(order);
        when(feedbackRepository.findByOrder(order)).thenReturn(any());
        FeedbackRequestDTO feedbackRequestDTO = FeedbackRequestDTO.builder()
                .orderId(1)
                .comment("test")
                .rating(5)
                .build();
        assertNotNull(feedbackService.create(feedbackRequestDTO));

    }

    @Test
    public void createFeedbackAlreadyExistsForOrder(){
        Order order = new Order();
        FeedbackRequestDTO feedbackRequestDTO = FeedbackRequestDTO.builder()
                .orderId(1)
                .comment("test")
                .rating(5)
                .build();
        when(orderService.getById(1)).thenReturn(order);
        when(feedbackRepository.findByOrder(order)).thenReturn(new Feedback());
        assertThrows(IllegalArgumentException.class, ()-> feedbackService.create(feedbackRequestDTO));
    }

    @Test
    public void getAllHappyFlow(){
        when(feedbackRepository.findAll()).thenReturn(Collections.emptyList());
        feedbackService.getAll();
    }

    @Test
    public void getByIdHappyFlow(){
        Feedback feedback = Feedback.builder()
                .feedbackId(1)
                .comment("test")
                .rating(5)
                .order(new Order())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        when(feedbackRepository.existsById(1)).thenReturn(true);
        when(feedbackRepository.findById(1)).thenReturn(Optional.of(feedback));
        assertNotNull(feedbackService.getById(1));

    }
    @Test
    public void getByIdNotFound(){
        when(feedbackRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()->feedbackService.getById(1));
    }

    @Test
    public void updateHappyFlow(){
        when(feedbackRepository.existsById(1)).thenReturn(true);
        FeedbackUpdateDTO feedbackUpdateDTO = FeedbackUpdateDTO.builder()
                .comment("test")
                .rating(5)
                .build();
        Feedback feedback = Feedback.builder()
                .feedbackId(1)
                .comment("test")
                .rating(5)
                .order(new Order())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        when(feedbackRepository.findById(1)).thenReturn(Optional.of(feedback));
        assertNotNull(feedbackService.update(1, feedbackUpdateDTO));
    }

    @Test
    public void updateIdNotFound(){
        when(feedbackRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> feedbackService.update(1,any()));
    }

    @Test
    public void deleteHappyFlow(){
        when(feedbackRepository.existsById(1)).thenReturn(true);
        feedbackService.delete(1);
    }

    @Test
    public void deleteIdNotFound(){
        when(feedbackRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()-> feedbackService.delete(1));
    }
}
