package com.example.tasks.saver.services.interfaces;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface OperationServiceInterface {
  ////  List<Operation> listAll();
    ////List<Operation> findSortAll(@NotNull Sort sort);
    ////Page<Operation> findAllPages(@NotNull Pageable pageable);
    //List<Operation> findByTaskName(String taskName);
    Operation save (Task task, Operation operation);
    void delete(long id);
   // Optional<Operation> findByOperationNumber(Long operationNumber);
   // Optional<Operation> findByTaskNameAndOperationName(String taskName, String operationName);
   // Optional<Operation> findByOperationName(String operationName);
}
