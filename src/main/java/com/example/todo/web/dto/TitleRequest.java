package com.example.todo.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TitleRequest(@NotBlank @Size(max = 120) String title) {}
