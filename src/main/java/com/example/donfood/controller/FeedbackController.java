package com.example.donfood.controller;

import com.example.donfood.dto.feedbackDTO.FeedbackRequestDTO;
import com.example.donfood.dto.feedbackDTO.FeedbackUpdateDTO;
import com.example.donfood.model.Feedback;
import com.example.donfood.service.feedbackService.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> getAll(){
        return ResponseEntity.ok().body(feedbackService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(feedbackService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Feedback> create(@RequestBody FeedbackRequestDTO feedbackRequestDTO){
        return ResponseEntity.ok().body(feedbackService.create(feedbackRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> update(@PathVariable Integer id, @RequestBody FeedbackUpdateDTO feedbackUpdateDTO){
        return ResponseEntity.ok().body(feedbackService.update(id, feedbackUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        feedbackService.delete(id);
        return "Ok";
    }

}
