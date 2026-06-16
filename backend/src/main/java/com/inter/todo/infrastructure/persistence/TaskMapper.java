package com.inter.todo.infrastructure.persistence;

import com.inter.todo.domain.model.Task;

public final class TaskMapper {

    private TaskMapper() {
    }

    public static TaskJpaEntity toEntity(Task task) {
        return new TaskJpaEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }

    public static Task toDomain(TaskJpaEntity entity) {
        return Task.restore(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
