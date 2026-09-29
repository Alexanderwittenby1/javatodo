package com.example.todo.web.dto;

import java.util.List;

public record BoardDto(int version, List<ColumnDto> columns, List<CardDto> cards) {}