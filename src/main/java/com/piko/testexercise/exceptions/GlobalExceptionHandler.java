package com.piko.testexercise.exceptions;

import com.piko.testexercise.controller.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AddressLimitException.class)
    public ResponseEntity<?> handleAddressLimit(AddressLimitException exception){
        return new ResponseEntity<>(
                new ErrorResponse(exception.getMessage(),
                        LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RecordDoesNotExistException.class)
    public ResponseEntity<?> handleRecordDoesNotExist(RecordDoesNotExistException exception){
        return new ResponseEntity<>(
                new ErrorResponse(exception.getMessage(),
                        LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeExc(RuntimeException exception){
        return new ResponseEntity<>(
                new ErrorResponse(exception.getMessage(),
                        LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
