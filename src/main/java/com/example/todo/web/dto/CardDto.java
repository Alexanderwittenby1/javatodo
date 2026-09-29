package com.example.todo.web.dto;

import java.time.Instant;

public record CardDto(
        String id, String columnId, String title, int order, Instant createdAt, Instant updatedAt) {}
