# Todo App - How To Guide

## Overview
This is a REST API for managing todo items, built with Spring Boot 3.2.0 and Java 17 using Test-Driven Development (TDD).

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- curl or Postman for testing

## Project Structure
```
TodoApp/
├── src/
│   ├── main/java/com/example/todoapp/
│   │   ├── TodoAppApplication.java       # Main Spring Boot application
│   │   ├── controller/
│   │   │   └── TodoController.java       # REST endpoints
│   │   ├── service/
│   │   │   └── TodoService.java          # Business logic
│   │   ├── repository/
│   │   │   └── TodoRepository.java       # In-memory data storage
│   │   ├── model/
│   │   │   └── Todo.java                 # Todo entity
│   │   └── exception/
│   │       ├── InvalidTodoException.java
│   │       └── GlobalExceptionHandler.java
│   └── test/java/com/example/todoapp/
│       └── controller/
│           └── TodoControllerTest.java   # Integration tests
├── pom.xml
├── TodoApp.postman_collection.json       # Postman collection
├── todo.md                               # Progress tracker
└── HOW_TO.md                            # This file
```

## Getting Started

### 1. Build the Project
```bash
mvn clean install
```

### 2. Run the Application
```bash
mvn spring-boot:run
```

The server starts on **http://localhost:8000**

### 3. Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TodoControllerTest
```

## API Endpoints

### POST /todos
Creates a new todo item.

**Request Body:**
```json
{
  "title": "Buy milk",
  "done": false
}
```
- `title` (required): The todo description (cannot be empty or whitespace)
- `done` (optional): Completion status (defaults to `false`)

**Success Response (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Buy milk",
  "done": false
}
```

**Error Responses:**

*Missing Title (400 Bad Request):*
```json
{
  "error": "Title is required"
}
```

*Empty Title (400 Bad Request):*
```json
{
  "error": "Title cannot be empty"
}
```

## Testing with curl

### Create a Todo
```bash
# Valid todo
curl -X POST http://localhost:8000/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Buy milk"}'

# Todo with done=true
curl -X POST http://localhost:8000/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Already completed","done":true}'
```

### Test Validation Errors
```bash
# Empty title
curl -X POST http://localhost:8000/todos \
  -H "Content-Type: application/json" \
  -d '{"title":""}'

# Missing title
curl -X POST http://localhost:8000/todos \
  -H "Content-Type: application/json" \
  -d '{}'
```

## Testing with Postman

1. Import the collection: `TodoApp.postman_collection.json`
2. The collection includes:
   - Valid requests (title only, with done=true)
   - Invalid requests (empty title, missing title, whitespace)

## Development Approach

This project follows **TDD (Test-Driven Development)**:
1. Write failing test
2. Implement minimal code to pass test
3. Refactor if needed
4. Repeat

### Adding New Features

1. Update `todo.md` with task breakdown
2. Write failing test in `TodoControllerTest.java`
3. Run test to verify it fails: `mvn test`
4. Implement feature iteratively (model → repository → service → controller)
5. Run test to verify it passes
6. Build and restart: `mvn clean install && mvn spring-boot:run`
7. Update `todo.md` with progress
8. Update Postman collection with new endpoints

## Architecture

### Layers
- **Controller**: HTTP request/response handling
- **Service**: Business logic and validation
- **Repository**: Data storage (in-memory with ConcurrentHashMap)
- **Model**: Domain entities
- **Exception**: Custom exceptions and global error handling

### Key Design Decisions
- **In-memory storage**: ConcurrentHashMap for thread-safety
- **UUID for IDs**: Generated automatically on todo creation
- **Default values**: `done` defaults to `false` if not provided
- **Validation**: Title required and cannot be empty/whitespace
- **Error handling**: Global exception handler returns meaningful error messages

## Troubleshooting

### Port Already in Use
If port 8000 is already in use, change it in `src/main/resources/application.properties`:
```properties
server.port=8080
```

### Tests Failing
```bash
# Clean and rebuild
mvn clean install

# Check for compilation errors
mvn compile
```

## Next Steps
See `todo.md` for planned features:
- PATCH /todos/{id} - Update existing todo
- GET /todos - List all todos with filtering
- Pagination support
- Metrics endpoint
- Idempotency support

## Resources
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Maven Documentation: https://maven.apache.org/guides/
