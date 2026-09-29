package com.example.todo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCardRequest(@NotBlank String columnId, @NotBlank @Size(max = 120) String title) {}
