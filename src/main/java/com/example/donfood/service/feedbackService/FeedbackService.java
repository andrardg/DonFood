package com.example.donfood.service.feedbackService;

import com.example.donfood.dto.feedbackDTO.FeedbackRequestDTO;
import com.example.donfood.dto.feedbackDTO.FeedbackUpdateDTO;
import com.example.donfood.exception.ResourceNotFoundException;
import com.example.donfood.mapper.FeedbackMapper;
import com.example.donfood.model.Feedback;
import com.example.donfood.model.Order;
import com.example.donfood.repository.IFeedbackRepository;
import com.example.donfood.service.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class FeedbackService implements IFeedbackService{
    @Autowired
    private IFeedbackRepository feedbackRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Feedback create(FeedbackRequestDTO feedbackRequestDTO) {
        Order order = orderService.getById(feedbackRequestDTO.getOrderId()); // to make sure it exists

        if(feedbackRepository.findByOrder(order) != null)
            throw new IllegalArgumentException("Cannot create multiple feedbacks for the same order");

        feedbackRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        Feedback feedback = FeedbackMapper.requestToFeedback(feedbackRequestDTO);
        //feedback.getOrder().setOrderId(feedback.getOrderId());
        //feedback.setOrder(new Order());
        feedbackRepository.save(feedback);
        return feedback;
    }

    @Override
    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback getById(Integer id) {
        if(!feedbackRepository.existsById(id))
            throw new ResourceNotFoundException("No Feedback with that id.");
        Feedback feedback = feedbackRepository.findById(id).get();
        return feedback;
    }

    @Override
    public Feedback update(Integer id, FeedbackUpdateDTO feedbackUpdateDTO) {
        if(!feedbackRepository.existsById(id))
            throw new ResourceNotFoundException("No Feedback with that id.");
        Feedback feedback = feedbackRepository.findById(id).get();

        if(feedbackUpdateDTO.getComment() != null)
            feedback.setComment(feedbackUpdateDTO.getComment());

        if(feedbackUpdateDTO.getRating() != null)
            feedback.setRating(feedbackUpdateDTO.getRating());

        feedbackRepository.save(feedback);
        return feedback;
    }

    @Override
    public void delete(Integer id) {
        if(!feedbackRepository.existsById(id))
            throw new ResourceNotFoundException("No Feedback with that id.");
        feedbackRepository.deleteById(id);
    }
}
