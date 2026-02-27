package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for managing todo items.
 */
@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Creates a new todo item.
     * @param request JSON body containing title and optional done status
     * @return 201 Created with the new todo
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Map<String, Object> request) {
        String title = request.get("title") != null ? (String) request.get("title") : null;
        Boolean done = request.get("done") != null ? (Boolean) request.get("done") : null;

        Todo todo = todoService.createTodo(title, done);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }
}
