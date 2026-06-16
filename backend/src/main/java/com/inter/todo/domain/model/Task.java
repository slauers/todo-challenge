package com.inter.todo.domain.model;

import com.inter.todo.domain.exception.InvalidTaskException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Task {

    private static final int TITLE_MAX_LENGTH = 255;

    private final UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private final Instant createdAt;

    private Task(UUID id, String title, String description, TaskStatus status, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        setTitle(title);
        this.description = description;
    }

    public static Task create(String title, String description) {
        return new Task(UUID.randomUUID(), title, description, TaskStatus.PENDING, Instant.now());
    }

    public static Task restore(UUID id, String title, String description, TaskStatus status, Instant createdAt) {
        return new Task(id, title, description, status, createdAt);
    }

    public void updateDetails(String title, String description) {
        setTitle(title);
        this.description = description;
    }

    public void updateStatus(TaskStatus status) {
        if (status == null) {
            throw new InvalidTaskException("status must not be null");
        }
        this.status = status;
    }

    private void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidTaskException("title must not be blank");
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new InvalidTaskException("title must have at most " + TITLE_MAX_LENGTH + " characters");
        }
        this.title = title.trim();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
