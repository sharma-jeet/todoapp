package com.example.todoapp.repository;

import com.example.todoapp.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository for storing and managing todo items.
 * Uses ConcurrentHashMap for thread-safe operations.
 */
@Repository
public class TodoRepository {
    private final Map<String, Todo> todos = new ConcurrentHashMap<>();

    /**
     * Saves a todo item to the repository.
     * @param todo the todo to save
     * @return the saved todo
     */
    public Todo save(Todo todo) {
        todos.put(todo.getId(), todo);
        return todo;
    }
}
