package com.example.todo.config;


import com.example.todo.domain.TodoColumn;
import com.example.todo.domain.TodoColumnRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BoardSeeder implements ApplicationRunner {

    private static final String[][] DEFAULTS = {
            { "todo", "Att göra" },
            { "doing", "Pågår" },
            { "done", "Klar" },
    };

    private final TodoColumnRepository columns;

    public BoardSeeder(TodoColumnRepository columns) {
        this.columns = columns;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (columns.count() > 0) return;

        for (int order = 0; order < DEFAULTS.length; order++) {
            columns.save(new TodoColumn(DEFAULTS[order][0], DEFAULTS[order][1], order));
        }
    }
}