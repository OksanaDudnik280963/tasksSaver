package com.example.tasks.saver.repositories;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("operationRepository")
public interface OperationRepository extends JpaRepository<Operation, Long> {
    ////List<Operation> findSortAll(@NotNull Sort sort);

   //// Page<Operation> findAllPages(@NotNull Pageable pageable);

    ////List<Operation> findByTaskName(String taskName);

    ////Optional<Operation> findByOperationName(String operationName);

    Optional<Operation> findByTaskNameAndOperationName(String taskName, String operationName);

    ////Optional<Operation> findByOperationNumber(Long operationNumber);
}
