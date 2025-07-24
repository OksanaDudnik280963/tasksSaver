package com.example.tasks.saver.services.implementations;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import com.example.tasks.saver.dto.enums.RoleName;
import com.example.tasks.saver.dto.enums.TasksStatus;
import com.example.tasks.saver.repositories.OperationRepository;
import com.example.tasks.saver.repositories.TaskRepository;
import com.example.tasks.saver.services.interfaces.TaskServiceInterface;
import com.example.tasks.saver.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.example.tasks.saver.global.InstallConstants.START_TASK_NAME;

@Slf4j
@Service
public class TaskService implements TaskServiceInterface {
    private final TaskRepository taskRepository;
    private final OperationRepository operationRepository;

    @Autowired
    public TaskService(TaskRepository repository, OperationRepository operationRepository) {
        this.taskRepository = repository;
        this.operationRepository = operationRepository;
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
                    .taskCost(BigDecimal.ZERO)
                    .taskDescription(START_TASK_NAME)
                    .taskName(START_TASK_NAME)
                    .taskStatus(TasksStatus.PROJECT.name())
                    .operationsCount(0L)
                    .build();
            task.setCreated(new Date());
            task.setUpdated(new Date());
            task.setCreatedBy(RoleName.MANAGER.name());
            task.setChangedBy(RoleName.MANAGER.name());
           // taskOptional = Optional.of(task);
            return task;
        } else {
            Task task = Task.builder()
                    .id((taskOptional.get().getId() != null) ? taskOptional.get().getId()
                            : (count() + 1L))
                    .taskCost((taskOptional.get().getTaskCost() == null) ? BigDecimal.ZERO
                            : taskOptional.get().getTaskCost())
                    .taskDescription((taskOptional.get().getTaskDescription().isEmpty()) ? START_TASK_NAME
                            : taskOptional.get().getTaskDescription())
                    .taskName((taskOptional.get().getTaskName().isEmpty()) ? START_TASK_NAME
                            : taskOptional.get().getTaskName())
                    .taskStatus((taskOptional.get().getTaskStatus() != null) ? taskOptional.get().getTaskStatus()
                            : TasksStatus.PROJECT.name())
                    .operationsCount((taskOptional.get().getOperationsCount() > 0L) ? taskOptional.get().getOperationsCount()
                            : 0L)
                    .build();
            task.setUpdated(new Date());
            task.setChangedBy(RoleName.MANAGER.name());
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
    public List<Operation> getOperationsByTaskId(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        if(task.isEmpty()){
            return new ArrayList<>();
        }
        return this.operationRepository.findByTaskName(task.get().getTaskName());
    }

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
