package com.example.todo.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "todo_columns")
public class TodoColumn {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 64)
    private String id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    protected TodoColumn() {

    }


    public TodoColumn(String id,String title, int sortOrder) {
        this.id = id;
        this.title = title;
        this.sortOrder = sortOrder;
    }

    @PrePersist
    void assignId() {
        if (id == null) id = UUID.randomUUID().toString();
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getOrder() { return sortOrder; }
    public void setOrder(int order) { this.sortOrder = order; }



}
