package com.inter.todo.api.controller;

import com.inter.todo.api.dto.TaskRequest;
import com.inter.todo.api.dto.TaskResponse;
import com.inter.todo.api.dto.UpdateTaskRequest;
import com.inter.todo.application.TaskService;
import com.inter.todo.domain.model.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        Task task = taskService.create(request.title(), request.description());
        URI location = URI.create("/api/tasks/" + task.getId());
        return ResponseEntity.created(location).body(TaskResponse.from(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAll() {
        List<TaskResponse> tasks = taskService.listAll().stream()
                .map(TaskResponse::from)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable UUID id) {
        Task task = taskService.getById(id);
        return ResponseEntity.ok(TaskResponse.from(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateTaskRequest request) {
        Task task = taskService.update(id, request.title(), request.description(), request.status());
        return ResponseEntity.ok(TaskResponse.from(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
