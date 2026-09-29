package com.example.todo.service;


import com.example.todo.domain.TodoCard;
import com.example.todo.domain.TodoCardRepository;
import com.example.todo.domain.TodoColumn;
import com.example.todo.domain.TodoColumnRepository;
import com.example.todo.web.dto.BoardDto;
import com.example.todo.web.dto.CardDto;
import com.example.todo.web.dto.ColumnDto;
import com.example.todo.web.dto.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoBoardService {

    private static final int BOARD_VERSION = 1;
    private static final String DEFAULT_COLUMN = "todo";

    private final TodoColumnRepository columns;
    private final TodoCardRepository cards;

    public TodoBoardService(TodoColumnRepository columns, TodoCardRepository cards) {
        this.columns = columns;
        this.cards = cards;
    }

    @Transactional(readOnly = true)
    public BoardDto readBoard() {
        return board();
    }

    @Transactional
    public BoardDto createColumn(String title) {
        columns.save(new TodoColumn(null, title, (int) columns.count()));
        return board();
    }

    @Transactional
    public BoardDto createCard(String columnId, String title) {
        TodoColumn column = requireColumn(columnId);
        cards.save(new TodoCard(column, title, (int) cards.countByColumn_Id(columnId)));
        return board();
    }

    @Transactional
    public BoardDto renameColumn(String columnId, String title) {
        requireColumn(columnId).setTitle(title);
        return board();
    }

    @Transactional
    public BoardDto renameCard(String cardId, String title) {
        requireCard(cardId).setTitle(title);
        return board();
    }

    @Transactional
    public BoardDto deleteColumn(String columnId) {
        requireColumn(columnId);
        cards.deleteByColumn_Id(columnId);
        columns.deleteById(columnId);
        return board();
    }

    @Transactional
    public BoardDto deleteCard(String cardId) {
        requireCard(cardId);
        cards.deleteById(cardId);
        return board();
    }

    @Transactional
    public BoardDto moveCard(String cardId, String toColumnId, int toIndex) {
        TodoCard card = requireCard(cardId);
        TodoColumn target = columns.findLockedById(toColumnId)
                .orElseThrow(() -> new NotFoundException("Kolumnen finns inte längre."));

        String fromColumnId = card.getColumnId();

        List<TodoCard> rest = new ArrayList<>(cards.findInColumn(toColumnId).stream()
                .filter(entry -> !entry.getId().equals(cardId))
                .toList());

        int index = Math.min(Math.max(toIndex, 0), rest.size());
        rest.add(index, card);

        card.setColumn(target);
        renumber(rest);

        if (!fromColumnId.equals(toColumnId)) {
            renumber(cards.findInColumn(fromColumnId));
        }

        return board();
    }

    private void renumber(List<TodoCard> cards) {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setOrder(i);
        }
    }

    private BoardDto board() {
        List<ColumnDto> columnDtos = columns.findAllByOrderByOrderAsc().stream()
                .map(column -> new ColumnDto(column.getId(), column.getTitle(), column.getOrder()))
                .toList();

        List<CardDto> cardDtos = cards.findAllInBoardOrder().stream()
                .map(card -> new CardDto(
                        card.getId(), card.getColumnId(), card.getTitle(),
                        card.getOrder(), card.getCreatedAt(), card.getUpdatedAt()))
                .toList();

        return new BoardDto(BOARD_VERSION, columnDtos, cardDtos);
    }

    private TodoColumn requireColumn(String columnId) {
        return columns.findById(columnId)
                .orElseThrow(() -> new NotFoundException("Kolumnen finns inte längre."));
    }

    private TodoCard requireCard(String cardId) {
        return cards.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Kortet finns inte längre."));
    }
}