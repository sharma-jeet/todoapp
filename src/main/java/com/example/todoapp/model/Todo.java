package com.example.todoapp.model;

import java.util.UUID;

/**
 * Represents a todo item with a unique ID, title, and completion status.
 */
public class Todo {
    private String id;
    private String title;
    private Boolean done;

    public Todo() {
    }

    /**
     * Creates a new todo with a generated UUID.
     * @param title the todo title
     * @param done the completion status (defaults to false if null)
     */
    public Todo(String title, Boolean done) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.done = done != null ? done : false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
