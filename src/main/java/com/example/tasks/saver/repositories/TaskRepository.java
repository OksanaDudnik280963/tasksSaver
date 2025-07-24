package com.example.tasks.saver.repositories;

import com.example.tasks.saver.dto.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<Task> findByTaskName(String taskName);
    Iterable<Task> findAll(Sort sort);
}
