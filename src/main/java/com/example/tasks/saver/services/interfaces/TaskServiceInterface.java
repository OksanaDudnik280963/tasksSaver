package com.example.tasks.saver.services.interfaces;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskServiceInterface {
    List<Task> findAll();
    Task fillTaskByDefault(Optional<Task> taskOptional);
    Long count();
    Task save(Task task);
    Optional<Task> get(Long id) throws Exception;
    List<Operation> getOperationsByTaskId(Long id);
    void delete(long id);
/*
    List<Task> findAll(Sort sort);
    Page<Task> findAll(Pageable pageable);
*/
    Page<Task> findPaginated(Pageable pageable);
}
