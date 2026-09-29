package com.example.todo.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "todo_cards",
        indexes = @Index(name = "idx_todo_cards_column_order",
                columnList = "column_id, sort_order"))
public class TodoCard {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 64)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "column_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_todo_cards_column"))
    private TodoColumn column;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "sort_order", nullable = false)
    private int order;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TodoCard() {}

    public TodoCard(TodoColumn column, String title, int order) {
        this.column = column;
        this.title = title;
        this.order = order;
    }

    @PrePersist
    void onCreate() {
        if (id == null) id = UUID.randomUUID().toString();
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public String getId() { return id; }
    public String getColumnId() { return column.getId(); }
    public TodoColumn getColumn() { return column; }
    public void setColumn(TodoColumn column) { this.column = column; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
