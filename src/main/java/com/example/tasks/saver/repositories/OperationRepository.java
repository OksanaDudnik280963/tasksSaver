package com.example.tasks.saver.repositories;

import com.example.tasks.saver.dto.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("operationRepository")
public interface OperationRepository extends CrudRepository<Operation, Long> {
   //// List<Operation> findSortAll(@NotNull Sort sort);
    //Page<Operation> findAllPages(@NotNull Pageable pageable);
    Optional<Operation> findByTaskNameAndOperationName(String taskName, String operationName);
    //List<Operation> findAll(PageRequest of);
    ////List<Operation> findByTaskName(String taskName);
    ////Optional<Operation> findByOperationName(String operationName);
    ////Optional<Operation> findByOperationNumber(Long operationNumber);
}
