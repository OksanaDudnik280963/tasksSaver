package com.example.tasks.saver.services.interfaces;

import com.example.tasks.saver.dto.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface OperationServiceInterface {
    //Operation save (Task task, Operation operation);
    Operation save (Operation operation);
    void delete(long id);
    Page<Operation> findPaginated(Pageable pageable);
    List<Operation> findByTaskName(String taskName);
    List<Operation> findAll(Sort sort);
    Page<Operation> findAll(Pageable pageable);
    Operation fillOperationByDefault(Optional<Operation> operationOptional);
}
