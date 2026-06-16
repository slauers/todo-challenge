package com.inter.todo.application;

import com.inter.todo.domain.event.TaskCreatedEvent;
import com.inter.todo.domain.event.TaskUpdatedEvent;
import com.inter.todo.domain.exception.InvalidTaskException;
import com.inter.todo.domain.exception.TaskNotFoundException;
import com.inter.todo.domain.model.Task;
import com.inter.todo.domain.model.TaskStatus;
import com.inter.todo.infrastructure.persistence.TaskJpaRepository;
import com.inter.todo.infrastructure.persistence.TaskJpaEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskJpaRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public TaskService(TaskJpaRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Task create(String title, String description) {
        if (title == null || title.isBlank()) {
            throw new InvalidTaskException("Title cannot be empty");
        }

        Task task = Task.create(title, description);
        TaskJpaEntity entity = new TaskJpaEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
        TaskJpaEntity saved = repository.save(entity);

        eventPublisher.publishEvent(new TaskCreatedEvent(
                saved.getId(),
                saved.getTitle(),
                saved.getStatus(),
                Instant.now()
        ));

        return toDomain(saved);
    }

    public List<Task> listAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    public Task getById(UUID id) {
        return repository.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional
    public Task update(UUID id, String title, String description, TaskStatus status) {
        if (title == null || title.isBlank()) {
            throw new InvalidTaskException("Title cannot be empty");
        }

        TaskJpaEntity entity = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        entity.setTitle(title);
        entity.setDescription(description);
        entity.setStatus(status);

        TaskJpaEntity updated = repository.save(entity);

        eventPublisher.publishEvent(new TaskUpdatedEvent(
                updated.getId(),
                updated.getTitle(),
                updated.getStatus(),
                Instant.now()
        ));

        return toDomain(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private Task toDomain(TaskJpaEntity entity) {
        return Task.restore(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
