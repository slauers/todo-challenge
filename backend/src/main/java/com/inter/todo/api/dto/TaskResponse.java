package com.inter.todo.api.dto;

import com.inter.todo.domain.model.Task;
import com.inter.todo.domain.model.TaskStatus;

import java.time.Instant;
import java.util.UUID;

/** Representação de uma tarefa retornada pela API. */
public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        Instant createdAt
) {

    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }
}
