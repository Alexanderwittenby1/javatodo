package com.example.todo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoCardRepository extends JpaRepository<TodoCard, String> {

    @Query("select c from TodoCard c where c.column.id = :columnId order by c.order asc")
    List<TodoCard> findInColumn(String columnId);

    @Query("select c from TodoCard c order by c.column.order asc, c.order asc")
    List<TodoCard> findAllInBoardOrder();

    long countByColumn_Id(String columnId);

    long deleteByColumn_Id(String columnId);
}