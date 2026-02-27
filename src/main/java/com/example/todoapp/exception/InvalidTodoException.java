package com.example.todoapp.exception;

/**
 * Exception thrown when todo validation fails.
 */
public class InvalidTodoException extends RuntimeException {
    public InvalidTodoException(String message) {
        super(message);
    }
}
