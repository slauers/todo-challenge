package com.inter.todo.domain.event;

import com.inter.todo.domain.model.TaskStatus;

import java.time.Instant;
import java.util.UUID;

/** Evento de domínio emitido após a criação de uma tarefa (snapshot imutável). */
public record TaskCreatedEvent(
        UUID taskId,
        String title,
        TaskStatus status,
        Instant occurredAt
) {
}
