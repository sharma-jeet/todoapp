## Overview

**Objective:** Evaluate technical depth, debugging ability, and understanding of coding agents through a real coding exercise.

**Candidate Instructions:**

- Build from scratch using your preferred programming language and framework.
- AI coding assistance is highly encouraged! Use whatever you're comfortable with (Cursor, Copilot, Claude, Codex, etc.).
- Narrate your thinking process and show your prompts.

## V1: Base Requirements

### Endpoints

- `POST /todos`

  Creates a todo. The "done" field should have a default of `false`, but can be explicitly set to `true`.

  **Body:** `{ "title": "Buy milk" }`

  **Defaults:** `done = false`

  **Response:** `201 { "id": "<uuid>", "title": "Buy milk", "done": false }`

- `PATCH /todos/{id}`

  Update a single todo item.

  **Body:** `{ "title": "Buy milk", "done": true }`

  **Response:** `200 { "id": "<uuid>", "title": "Buy milk", "done": true }`

- `GET /todos`

  Lists todos. Optional substring filter by `title`.

  **Query param:** `?query=milk`

  **Response:**

    ```json
    {
      "items": [ { "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6", "title": "Buy milk", "done": false } ],
      "total": 1
    }
    
    ```

### Acceptance Checks

```bash
curl -s -X POST http://localhost:8000/todos \
  -H 'Content-Type: application/json' \
  -d '{"title":"Buy milk"}' | jq

curl -s -X PATCH http://localhost:8000/todos/some-uuid-from-last-step \
  -H 'Content-Type: application/json' \
  -d '{"done": true}' | jq
  
curl -s 'http://localhost:8000/todos?query=milk' | jq

```

Expect `{"title": "Buy milk", "done": true}` to appear in results.

---

## V2: Extensions (choose whatever you like)

### 1. Frontend
Build a frontend where people can see, update, and create todo items.

### 2. Pagination

- Add `page` and `per_page` query params to `GET /todos`.
- Default: `page=1`, `per_page=10`.
- Response includes: `page`, `per_page`, and correct `items` slice.

### 3. Metrics Endpoint

- Add `GET /metrics` returning request counts.
- `total_requests` counts all requests. `total_4xx` and `_5xx` count requests whose response status codes start with a 4 or 5 respectively.
- For example, suppose the client tries to update a todo which doesn't exist. If your `PATCH` endpoint uses status code 404 for its response in this situation, then the request should increment both `total_requests` and `total_4xx`.
```json
{
  "total_requests": 14,
  "total_4xx": 5,
  "total_5xx": 0
}

```

```bash
curl -s http://localhost:8000/metrics | jq
```

### 4. Idempotency

- Support `X-Idempotency-Key` header on `POST /todos`.
- If a request with the same key repeats, return the **same todo** with HTTP 200.
   - If that todo has been updated in the meantime, return the current, updated version of the todo.
- You don't need to support `X-Idempotency-Key` for other endpoints, only for the create endpoint.

### V2 Acceptance Checks
```bash
# Pagination
curl -s 'http://localhost:8000/todos?page=1&per_page=2' | jq
curl -s 'http://localhost:8000/todos?page=2&per_page=2' | jq

# Idempotency
curl -s -i -X POST http://localhost:8000/todos \
  -H 'Content-Type: application/json' \
  -H 'X-Idempotency-Key: abc123' \
  -d '{"title":"Unique"}'

curl -s -i -X POST http://localhost:8000/todos \
  -H 'Content-Type: application/json' \
  -H 'X-Idempotency-Key: abc123' \
  -d '{"title":"Unique"}'
```