package com.example.tasks.saver.services.interfaces;

import com.example.tasks.saver.dto.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TaskServiceInterface {
    ////List<Task> findSortAll(Sort sort);
    ////Page<Task> findPageableAll(Pageable pageable);
    Task save(Task task);
    Task get(Long id);
    void delete(long id);
}
