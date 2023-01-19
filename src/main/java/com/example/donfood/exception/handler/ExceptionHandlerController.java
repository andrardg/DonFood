package com.example.donfood.exception.handler;

import com.example.donfood.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({EntityExistsException.class})
    public ErrorResponse handleEntityExistsException(EntityExistsException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorResponse handleMethodArgumentNotValidException (MethodArgumentNotValidException exception) {
        return new ErrorResponse(exception.getMessage(), LocalDateTime.now());
    }
}
