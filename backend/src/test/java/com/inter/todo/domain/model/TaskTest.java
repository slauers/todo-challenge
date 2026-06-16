package com.inter.todo.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testCreateTask() {
        Task task = Task.create("Test Title", "Test Description");

        assertNotNull(task.getId());
        assertEquals("Test Title", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals(TaskStatus.PENDING, task.getStatus());
        assertNotNull(task.getCreatedAt());
    }

    @Test
    void testTaskTitle() {
        Task task = Task.create("My Task", null);
        assertEquals("My Task", task.getTitle());
    }

    @Test
    void testTaskInitialStatus() {
        Task task = Task.create("Task", "Desc");
        assertEquals(TaskStatus.PENDING, task.getStatus());
    }
}
