package com.example.todo.web.dto;

import com.example.todo.service.TodoBoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoBoardController {

    private final TodoBoardService service;

    public TodoBoardController(TodoBoardService service) {
        this.service = service;
    }

    @GetMapping("/board")
    public BoardDto board() {
        return service.readBoard();
    }

    @PostMapping("/columns")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDto createColumn(@Valid @RequestBody TitleRequest body) {
        return service.createColumn(body.title());
    }

    @PatchMapping("/columns/{columnId}")
    public BoardDto renameColumn(@PathVariable String columnId, @Valid @RequestBody TitleRequest body) {
        return service.renameColumn(columnId, body.title());
    }

    @DeleteMapping("/columns/{columnId}")
    public BoardDto deleteColumn(@PathVariable String columnId) {
        return service.deleteColumn(columnId);
    }

    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDto createCard(@Valid @RequestBody CreateCardRequest body) {
        return service.createCard(body.columnId(), body.title());
    }

    @PatchMapping("/cards/{cardId}")
    public BoardDto renameCard(@PathVariable String cardId, @Valid @RequestBody TitleRequest body) {
        return service.renameCard(cardId, body.title());
    }

    @PutMapping("/cards/{cardId}/position")
    public BoardDto moveCard(@PathVariable String cardId, @Valid @RequestBody MoveCardRequest body) {
        return service.moveCard(cardId, body.columnId(), body.index());
    }

    @DeleteMapping("/cards/{cardId}")
    public BoardDto deleteCard(@PathVariable String cardId) {
        return service.deleteCard(cardId);
    }
}