package com.example.tasks.saver.services.implementations;

import com.example.tasks.saver.dto.Task;
import com.example.tasks.saver.dto.enums.TasksStatus;
import com.example.tasks.saver.repositories.TaskRepository;
import com.example.tasks.saver.services.interfaces.TaskServiceInterface;
import com.example.tasks.saver.utils.JsonUtils;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service("taskService")
//@Transactional
public class TaskService implements TaskServiceInterface {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.taskRepository = repository;
    }

    public Long count() {
        return (long) this.findAll().size();
    }

    public Task fillTaskByDefault(Optional<Task> taskOptional) {
        if (taskOptional.isPresent() && log.isDebugEnabled()) {
            log.info("Filling = {}", JsonUtils.toJson(taskOptional));
        }
        if (taskOptional.isEmpty()) {
            Task task = Task.builder()
                    //.id(count() + 1L)
                    .taskCost(BigDecimal.ZERO)
                    .taskDescription("New task")
                    .taskName("New task")
                    .taskStatus(TasksStatus.PROJECT.name())
                    .operationsCount(0L)
                    .build();
            task.setCreated(new Date());
            task.setUpdated(new Date());
            task.setCreatedBy("admin");
            task.setChangedBy("admin");
            taskOptional = Optional.of(task);
            return taskOptional.get();
        } else {
            Task task = Task.builder()
                    .id((taskOptional.get().getId() != null) ? taskOptional.get().getId() : (count() + 1L))
                    .taskCost((taskOptional.get().getTaskCost() == null) ? BigDecimal.ZERO : taskOptional.get().getTaskCost())
                    .taskDescription((taskOptional.get().getTaskDescription().isEmpty()) ? "New task" : taskOptional.get().getTaskDescription())
                    .taskName((taskOptional.get().getTaskName().isEmpty()) ? "New task" : taskOptional.get().getTaskName())
                    .taskStatus((taskOptional.get().getTaskStatus() != null) ? taskOptional.get().getTaskStatus() : TasksStatus.PROJECT.name())
                    .operationsCount((taskOptional.get().getOperationsCount() > 0L) ? taskOptional.get().getOperationsCount() : 0L)
                    .build();
            task.setUpdated(new Date());
            task.setChangedBy("admin");
            taskOptional = Optional.of(task);
            return taskOptional.get();
        }
    }

    public List<Task> findAll() {
        return (List<Task>) this.taskRepository.findAll(Sort.by("id"));
    }

    public Task save(Task task) {
        if (task == null) {
            log.error("Task is not filled.");
            return null;
        } else {
            if (log.isDebugEnabled()) {
                log.info(JsonUtils.toJson(task));
            }

            Optional<Task> taskOptional = ((task.getId() != null) ?
                    this.taskRepository.findById(task.getId()) :
                    this.taskRepository.findByTaskName(task.getTaskName()));
            if (taskOptional.isPresent()) {
                log.info("Saving of task = {}", taskOptional.get());
                if (task.equals(taskOptional.get())) {
                    return taskOptional.get();
                } else {
                    log.info("Task {} already exist", task.getTaskName());

                    Task realTask = taskOptional.get();

                    Task updated = Task.builder()
                            .id(realTask.getId())
                            //.taskNumber(realTask.getTaskNumber())
                            .taskName(task.getTaskName())
                            .taskDescription(task.getTaskDescription())
                            .taskStatus(task.getTaskStatus())
                            .taskCost(task.getTaskCost())
                            .operationsCount(task.getOperationsCount())
                            .build();
                    this.taskRepository.save(updated);
                    return updated;
                }
            } else {
                this.taskRepository.save(task);
                return task;
            }
        }
    }

    private boolean existsById(Long id) {
        return this.taskRepository.existsById(id);
    }

    public Optional<Task> get(Long id) throws Exception {
        Optional<Task> value = this.taskRepository.findById(id);
        return Optional.ofNullable(value.orElseThrow(() -> new Exception("Id is wrong!")));
    }

   // @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void delete(long id) throws NoSuchElementException {
        if (!existsById(id)) {
            throw new NoSuchElementException();
        } else {
            log.info("Deleting task with id = {}", id);
            Optional<Task> task = this.taskRepository.findById(id);
            task.ifPresent(this.taskRepository::delete);
        }
    }

    @Override
    public List<Task> findAll(Sort sort) {
        return (List<Task>) this.taskRepository.findAll(Sort.by("ID"));
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return (Page<Task>) this.taskRepository.findAll((Sort) pageable);
    }

    @Override
    public Page<Task> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Task> list;
        List<Task> tasks = findAll();
        if (tasks.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, tasks.size());
            list = tasks.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), tasks.size());
    }

}
