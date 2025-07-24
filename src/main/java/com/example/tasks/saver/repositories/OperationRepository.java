package com.example.tasks.saver.repositories;

import com.example.tasks.saver.dto.Operation;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {
    List<Operation> findByTaskName(String taskName);
    Iterable<Operation> findAll(Sort sort);
    Optional<Operation> findByOperationName(String operationName);
}
