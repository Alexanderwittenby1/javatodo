package com.example.todo.web.dto;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}