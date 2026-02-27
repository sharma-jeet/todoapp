# Todo App Development Tracker

## Phase 1: Project Setup & Foundation
- [x] Choose tech stack and framework (Spring Boot + Java)
- [x] Initialize project structure (Maven structure)
- [x] Create pom.xml with dependencies (Web, Validation, Test, Lombok)
- [x] Set up basic server with health check endpoint
- [x] Configure development environment (application.properties)

## Phase 2: V1 - Core Todo CRUD Operations

### Database & Models
- [x] Design Todo data model (id, title, done fields)
- [x] Set up in-memory data store (ConcurrentHashMap)
- [x] Implement UUID generation for todo IDs

### POST /todos endpoint
- [x] Create route handler for POST /todos
- [x] Create TodoRepository with save method
- [x] Create TodoService with createTodo method
- [x] Create TodoController with POST endpoint
- [x] Set default value for done field (false)
- [x] Return 201 with created todo object
- [ ] Implement request validation (title required)
- [ ] Add error handling for invalid requests

### PATCH /todos/{id} endpoint
- [ ] Create route handler for PATCH /todos/{id}
- [ ] Implement todo lookup by ID
- [ ] Support partial updates (title and/or done)
- [ ] Return 200 with updated todo
- [ ] Handle 404 for non-existent todos

### GET /todos endpoint
- [ ] Create route handler for GET /todos
- [ ] Implement basic listing of all todos
- [ ] Add query parameter support (?query=...)
- [ ] Implement substring filtering on title
- [ ] Return response with items array and total count

### V1 Testing
- [ ] Test POST /todos with curl
- [ ] Test PATCH /todos/{id} with curl
- [ ] Test GET /todos with query filter
- [ ] Verify acceptance criteria are met

## Phase 3: V2 Extensions

### Extension 1: Pagination
- [ ] Add page and per_page query parameters to GET /todos
- [ ] Implement pagination logic (default page=1, per_page=10)
- [ ] Calculate correct slice of items
- [ ] Include page and per_page in response
- [ ] Test pagination with curl

### Extension 2: Metrics Endpoint
- [ ] Design metrics tracking mechanism
- [ ] Implement middleware to count all requests
- [ ] Track 4xx status codes
- [ ] Track 5xx status codes
- [ ] Create GET /metrics endpoint
- [ ] Return total_requests, total_4xx, total_5xx
- [ ] Test metrics endpoint with curl

### Extension 3: Idempotency
- [ ] Design idempotency key storage mechanism
- [ ] Add X-Idempotency-Key header support to POST /todos
- [ ] Store mapping of idempotency key to todo ID
- [ ] Return existing todo if key exists (200 instead of 201)
- [ ] Return updated version of todo if it was modified
- [ ] Test idempotency with duplicate requests

### Extension 4: Frontend (Optional)
- [ ] Choose frontend framework
- [ ] Set up frontend project structure
- [ ] Create todo list view component
- [ ] Implement create todo form
- [ ] Implement update todo functionality
- [ ] Connect frontend to backend API
- [ ] Add basic styling

## Phase 4: Polish & Documentation
- [ ] Add comprehensive error handling
- [ ] Add input validation and sanitization
- [ ] Write README with setup instructions
- [ ] Document API endpoints
- [ ] Add comments to complex code sections
