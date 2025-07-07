package com.example.tasks.saver.repositories;

import com.example.tasks.saver.dto.Operation;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("operationRepository")
public interface OperationRepository extends CrudRepository<Operation, Long> {
    Optional<Operation> findByTaskNameAndOperationName(String taskName, String operationName);
    Iterable<Operation> findAll(Sort sort);
    ////List<Operation> findByTaskName(String taskName);
    Optional<Operation> findByOperationName(String operationName);
}
