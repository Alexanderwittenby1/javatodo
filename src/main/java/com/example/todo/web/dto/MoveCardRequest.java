package com.example.todo.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MoveCardRequest(@NotBlank String columnId, @Min(0) int index) {}
