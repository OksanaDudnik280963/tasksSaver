package com.example.tasks.saver.services.implementations;

import com.example.tasks.saver.dto.Task;
import com.example.tasks.saver.repositories.TaskRepository;
import com.example.tasks.saver.services.interfaces.TaskServiceInterface;
import com.example.tasks.saver.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service("taskService")
@Transactional
public class TaskService implements TaskServiceInterface {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.taskRepository = repository;
    }

/*
    public List<Task> findAll() {
        return new ArrayList<>(this.taskRepository.findAll());
    }

    public List<Task> findSortAll(Sort sort){
        return this.taskRepository.findSortAll(sort);
    }

    public Page<Task> findPageableAll(Pageable pageable){
        return this.taskRepository.findPageableAll(pageable);
    }

*/
    public Task save(Task task) {
        if (task != null) {
            if (log.isDebugEnabled()) {
                log.info(JsonUtils.toJson(task));
            }
        } else {
            log.error("Task not filled.");
            return null;
        }
        Optional<Task> taskOptional = this.taskRepository.findByTaskName(task.getTaskName());
        if (taskOptional.isPresent()) {
            log.info("Saving of user = {}", taskOptional);
            if (task.equals(taskOptional.get())) {
                return taskOptional.get();
            } else {
                log.info("User {} already exist", task.getTaskName());

                Task realTask = taskOptional.get();
                Task updated = Task.builder()
                        .id(realTask.getId())
                        //.taskNumber(realTask.getTaskNumber())
                        .taskName(realTask.getTaskName())
                        .taskDescription(realTask.getTaskDescription())
                        .taskStatus(realTask.getTaskStatus())
                        .taskCost(realTask.getTaskCost())
                        .operationsCount(realTask.getOperationsCount())
                        .build();
                return this.taskRepository.save(updated);
            }
        } else {
            return this.taskRepository.save(task);
        }

    }

    private boolean existsById(Long id) {
        return this.taskRepository.existsById(id);
    }

    public Task get(Long id) {
        Optional<Task> value = this.taskRepository.findById(id);
        if (value.isPresent()) {
            return value.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void delete(long id) {
        if (!existsById(id)) {
            throw new NoSuchElementException();
        } else {
            this.taskRepository.deleteById(id);
        }
    }
}
