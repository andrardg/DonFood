package com.example.donfood.mapper;

import com.example.donfood.dto.feedbackDTO.FeedbackRequestDTO;
import com.example.donfood.model.Feedback;
import org.modelmapper.ModelMapper;

public class FeedbackMapper {
    public static Feedback requestToFeedback(FeedbackRequestDTO feedbackRequestDTO){
        ModelMapper modelMapper = new ModelMapper();
        Feedback feedback = modelMapper.map(feedbackRequestDTO, Feedback.class);
        return feedback;
    }
}
