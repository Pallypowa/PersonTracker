package com.piko.testexercise.controller;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(String message, LocalDateTime timestamp, HttpStatus status) {
}
