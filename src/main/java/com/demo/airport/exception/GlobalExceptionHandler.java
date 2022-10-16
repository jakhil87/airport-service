package com.demo.airport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RecordNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRecordNotFound(RecordNotFoundException ex){
        return ErrorResponse.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .message(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRecordNotFound(ConstraintViolationException ex){
        return ErrorResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .build();
    }


}
