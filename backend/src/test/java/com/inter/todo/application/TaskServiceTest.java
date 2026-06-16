package com.inter.todo.application;

import com.inter.todo.domain.exception.TaskNotFoundException;
import com.inter.todo.domain.model.Task;
import com.inter.todo.domain.model.TaskStatus;
import com.inter.todo.infrastructure.persistence.TaskJpaEntity;
import com.inter.todo.infrastructure.persistence.TaskJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskJpaRepository repository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask() {
        UUID id = UUID.randomUUID();
        TaskJpaEntity entity = new TaskJpaEntity(id, "Test", "Desc", TaskStatus.PENDING, Instant.now());

        when(repository.save(any())).thenReturn(entity);

        Task result = taskService.create("Test", "Desc");

        assertNotNull(result.getId());
        assertEquals("Test", result.getTitle());
        assertEquals(TaskStatus.PENDING, result.getStatus());
    }

    @Test
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getById(id));
    }

    @Test
    void testDeleteNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.delete(id));
    }
}
