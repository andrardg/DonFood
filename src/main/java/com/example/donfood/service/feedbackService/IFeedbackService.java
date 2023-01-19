package com.example.donfood.service.feedbackService;

import com.example.donfood.dto.feedbackDTO.FeedbackRequestDTO;
import com.example.donfood.dto.feedbackDTO.FeedbackUpdateDTO;
import com.example.donfood.model.Feedback;

import java.util.List;

public interface IFeedbackService {
    Feedback create(FeedbackRequestDTO feedbackRequestDTO);
    List<Feedback> getAll();
    Feedback getById(Integer id);
    Feedback update(Integer id, FeedbackUpdateDTO feedbackUpdateDTO);
    void delete(Integer id);
}
