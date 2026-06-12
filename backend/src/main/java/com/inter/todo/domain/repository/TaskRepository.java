package com.inter.todo.domain.repository;

import com.inter.todo.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de persistência do domínio. Implementada na camada de infraestrutura
 * (adapter JPA), mantendo o domínio independente de framework.
 */
public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(UUID id);

    List<Task> findAll();

    void deleteById(UUID id);

    boolean existsById(UUID id);
}
