package com.example.donfood.dto.feedbackDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class FeedbackRequestDTO {

    @NotNull
    private Integer orderId;

    @NotNull
    private String comment;

    @NotNull
    private Integer rating;

    private Timestamp createdAt;
}
