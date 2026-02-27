package com.example.todoapp.exception;

/**
 * Exception thrown when a requested todo is not found.
 */
public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String id) {
        super("Todo not found with id: " + id);
    }
}
