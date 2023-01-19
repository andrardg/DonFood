package com.example.donfood.dto.feedbackDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeedbackUpdateDTO {

    private String comment;

    private Integer rating;
}
