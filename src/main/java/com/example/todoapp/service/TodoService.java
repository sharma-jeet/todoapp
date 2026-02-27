package com.example.todoapp.service;

import com.example.todoapp.exception.InvalidTodoException;
import com.example.todoapp.exception.TodoNotFoundException;
import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer for todo business logic.
 */
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Creates a new todo item.
     * @param title the todo title
     * @param done the completion status (defaults to false if null)
     * @return the created todo
     * @throws InvalidTodoException if title is null or empty
     */
    public Todo createTodo(String title, Boolean done) {
        validateTitle(title);
        Todo todo = new Todo(title, done);
        return todoRepository.save(todo);
    }

    /**
     * Updates an existing todo item.
     * @param id the todo ID
     * @param title the new title (optional)
     * @param done the new done status (optional)
     * @return the updated todo
     * @throws TodoNotFoundException if todo not found
     * @throws InvalidTodoException if title is provided but empty
     */
    public Todo updateTodo(String id, String title, Boolean done) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        if (title != null) {
            validateTitle(title);
            todo.setTitle(title);
        }
        if (done != null) {
            todo.setDone(done);
        }

        return todoRepository.save(todo);
    }

    /**
     * Validates the todo title.
     * @param title the title to validate
     * @throws InvalidTodoException if title is null or empty
     */
    private void validateTitle(String title) {
        if (title == null) {
            throw new InvalidTodoException("Title is required");
        }
        if (title.trim().isEmpty()) {
            throw new InvalidTodoException("Title cannot be empty");
        }
    }
}
