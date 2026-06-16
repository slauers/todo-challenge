package com.inter.todo.infrastructure.persistence;

import com.inter.todo.domain.model.Task;
import com.inter.todo.domain.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskJpaRepository jpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskJpaEntity entity = TaskMapper.toEntity(task);
        TaskJpaEntity saved = jpaRepository.save(entity);
        return TaskMapper.toDomain(saved);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return jpaRepository.findById(id).map(TaskMapper::toDomain);
    }

    @Override
    public List<Task> findAll() {
        return jpaRepository.findAll().stream()
                .map(TaskMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
