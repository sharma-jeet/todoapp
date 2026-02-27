package com.example.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTodo_withTitleOnly_shouldReturnCreatedTodoWithDefaultDoneFalse() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("title", "Buy milk");

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Buy milk"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void createTodo_withEmptyTitle_shouldReturn400() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("title", "");

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void createTodo_withNullTitle_shouldReturn400() throws Exception {
        Map<String, Object> request = new HashMap<>();

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void updateTodo_shouldUpdateAndReturn200() throws Exception {
        // First create a todo
        Map<String, Object> createRequest = new HashMap<>();
        createRequest.put("title", "Original title");

        String createResponse = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String todoId = objectMapper.readTree(createResponse).get("id").asText();

        // Now update it
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("title", "Updated title");
        updateRequest.put("done", true);

        mockMvc.perform(patch("/todos/" + todoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoId))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void updateTodo_withNonExistentId_shouldReturn404() throws Exception {
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("done", true);

        mockMvc.perform(patch("/todos/non-existent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void updateTodo_withEmptyTitle_shouldReturn400() throws Exception {
        // First create a todo
        Map<String, Object> createRequest = new HashMap<>();
        createRequest.put("title", "Original title");

        String createResponse = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String todoId = objectMapper.readTree(createResponse).get("id").asText();

        // Try to update with empty title
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("title", "");

        mockMvc.perform(patch("/todos/" + todoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error").value("Title cannot be empty"));
    }

    @Test
    void updateTodo_onlyDoneField_shouldUpdateOnlyDone() throws Exception {
        // First create a todo
        Map<String, Object> createRequest = new HashMap<>();
        createRequest.put("title", "Original title");
        createRequest.put("done", false);

        String createResponse = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String todoId = objectMapper.readTree(createResponse).get("id").asText();

        // Update only done field
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("done", true);

        mockMvc.perform(patch("/todos/" + todoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoId))
                .andExpect(jsonPath("$.title").value("Original title"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void updateTodo_onlyTitleField_shouldUpdateOnlyTitle() throws Exception {
        // First create a todo
        Map<String, Object> createRequest = new HashMap<>();
        createRequest.put("title", "Original title");
        createRequest.put("done", false);

        String createResponse = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String todoId = objectMapper.readTree(createResponse).get("id").asText();

        // Update only title field
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("title", "New title");

        mockMvc.perform(patch("/todos/" + todoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoId))
                .andExpect(jsonPath("$.title").value("New title"))
                .andExpect(jsonPath("$.done").value(false));
    }
}
