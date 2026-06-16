package com.inter.todo.infrastructure.event;

import com.inter.todo.domain.event.TaskCreatedEvent;
import com.inter.todo.domain.event.TaskUpdatedEvent;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {

    private static final Logger log = LoggerFactory.getLogger(TaskEventListener.class);

    @Async
    @EventListener
    public void handleTaskCreatedEvent(TaskCreatedEvent event) {
        log.info(
            "task event processed",
            StructuredArguments.kv("event", "TASK_CREATED"),
            StructuredArguments.kv("taskId", event.taskId()),
            StructuredArguments.kv("status", event.status()),
            StructuredArguments.kv("occurredAt", event.occurredAt())
        );
    }

    @Async
    @EventListener
    public void handleTaskUpdatedEvent(TaskUpdatedEvent event) {
        log.info(
            "task event processed",
            StructuredArguments.kv("event", "TASK_UPDATED"),
            StructuredArguments.kv("taskId", event.taskId()),
            StructuredArguments.kv("status", event.status()),
            StructuredArguments.kv("occurredAt", event.occurredAt())
        );
    }
}
