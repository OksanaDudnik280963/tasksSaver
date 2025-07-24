package com.example.tasks.saver.services.implementations;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import com.example.tasks.saver.dto.enums.OperationStatus;
import com.example.tasks.saver.dto.enums.RoleName;
import com.example.tasks.saver.repositories.OperationRepository;
import com.example.tasks.saver.repositories.TaskRepository;
import com.example.tasks.saver.services.interfaces.OperationServiceInterface;
import com.example.tasks.saver.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.example.tasks.saver.global.InstallConstants.START_OPERATION_NAME;
import static com.example.tasks.saver.global.InstallConstants.START_TASK_NAME;

@Slf4j
@Service
public class OperationService implements OperationServiceInterface {
    private final OperationRepository operationRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository, TaskRepository taskRepository) {
        this.operationRepository = operationRepository;
        this.taskRepository = taskRepository;
    }

    public Operation save(Operation operation) {
        if (operation == null) {
            log.error("Operation is not filled.");
            return null;
        } else {
            if (log.isDebugEnabled()) {
                log.info(JsonUtils.toJson(operation));
            }

            Optional<Operation> operationOptional = ((operation.getId() != null) ?
                    this.operationRepository.findById(operation.getId()) :
                    this.operationRepository.findByOperationName(operation.getOperationName()));
            if (operationOptional.isPresent()) {
                log.info("Saving of operation = {}", operationOptional.get());
                if (operation.equals(operationOptional.get())) {
                    return operationOptional.get();
                } else {
                    log.info("Operation {} already exist", operation.getTaskName());

                    Operation realOperation = operationOptional.get();
                    Optional<Task> parentTask = taskRepository.findByTaskName(realOperation.getTaskName());
                    Operation updatedOperation = Operation.builder()
                            .id(realOperation.getId())
                            .operationName(realOperation.getOperationName())
                            .taskName(parentTask.isPresent() ? parentTask.get().getTaskName() : operation.getTaskName())
                            .operationDescription(operation.getOperationDescription())
                            .operationStatus(operation.getOperationStatus())
                            .operationPrice(operation.getOperationPrice())
                            .build();
                    return this.operationRepository.save(updatedOperation);
                }
            } else {
                return this.operationRepository.save(operation);
            }
        }
    }

/*
    public Operation save(Task task, Operation operation) {
        if (operation != null) {
            if (log.isDebugEnabled()) {
                log.info(JsonUtils.toJson(operation));
            }
        } else {
            log.error("Operation not filled.");
            return null;
        }
        Optional<Operation> operationOptional = this.operationRepository.findById(operation.getId());
        this.operationRepository.findByOperationName(operation.getOperationName());
        if (operationOptional.isPresent()) {
            log.info("Saving of operation = {}", operationOptional);
            if (operation.equals(operationOptional.get())) {
                return operationOptional.get();
            } else {
                log.info("Operation {} already exist ", operation.getOperationName());

                Operation realOperation = operationOptional.get();
                Operation updated = Operation.builder()
                        .id(realOperation.getId())
                        .taskName(realOperation.getTaskName())
                        .operationDescription(realOperation.getOperationDescription())
                        .operationStatus(operation.getOperationStatus())
                        .operationPrice(realOperation.getOperationPrice())
                        .build();
                return this.operationRepository.save(updated);
            }
        } else {
            return this.operationRepository.save(operation);
        }

    }
*/

    private boolean existsById(Long id) {
        return this.operationRepository.existsById(id);
    }

    // @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void delete(long id) {
        if (!existsById(id)) {
            throw new NoSuchElementException();
        } else {
            this.operationRepository.deleteById(id);
        }
    }


    @Override
    public List<Operation> findByTaskName(String taskName) {
        List<Operation> operationList = findAll();
        return operationList.stream().filter(op -> op.getTaskName().equals(taskName))
                .distinct()
                .sorted()
                .toList();

    }

    @Override
    public List<Operation> findAll(Sort sort) {
        return (List<Operation>) this.operationRepository.findAll(Sort.by("ID"));
    }

    @Override
    public Page<Operation> findAll(Pageable pageable) {
        return (Page<Operation>) this.operationRepository.findAll((Sort) pageable);
    }

    public List<Operation> findAll() {
        return (List<Operation>) this.operationRepository.findAll(Sort.by("id"));
    }

    @Override
    public Page<Operation> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Operation> list;
        List<Operation> operations = findAll();
        if (operations.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, operations.size());
            list = operations.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), operations.size());
    }

    public Long count() {
        return (long) this.findAll().size();
    }

    public Operation fillOperationByDefault(Optional<Operation> operationOptional) {
        if (operationOptional.isEmpty()) {
            Operation operation = Operation.builder()
                    .operationDescription(START_OPERATION_NAME)
                    .operationName(START_OPERATION_NAME)
                    .operationStatus(OperationStatus.START.name())
                    .operationPrice(BigDecimal.ZERO)
                    .taskName(START_TASK_NAME)
                    .build();
            operation.setCreated(new Date());
            operation.setUpdated(new Date());
            operation.setCreatedBy(RoleName.MANAGER.name());
            operation.setChangedBy(RoleName.MANAGER.name());
            //operationOptional = Optional.of(operation);
            return operation;
        } else {
            if (log.isDebugEnabled()) {
                log.info("Filling = {}", JsonUtils.toJson(operationOptional));
            }

            Operation operation = Operation.builder()
                    .id((operationOptional.get().getId() != null) ?
                            operationOptional.get().getId() : (count() + 1L))
                    .operationName(operationOptional.get().getOperationName() == null ?
                            START_OPERATION_NAME : operationOptional.get().getOperationName())
                    .operationPrice((operationOptional.get().getOperationPrice() == null) ?
                            BigDecimal.ZERO : operationOptional.get().getOperationPrice())
                    .operationDescription(operationOptional.get().getOperationDescription() == null ?
                            operationOptional.get().getOperationName() : operationOptional.get().getOperationDescription())
                    .taskName((operationOptional.get().getTaskName() == null) ?
                            START_TASK_NAME : operationOptional.get().getTaskName())
                    .operationStatus((operationOptional.get().getOperationStatus() != null) ?
                            operationOptional.get().getOperationStatus() : OperationStatus.START.name())
                    .build();
            operation.setUpdated(new Date());
            operation.setChangedBy(RoleName.MANAGER.name());
            operationOptional = Optional.of(operation);
            return operationOptional.get();
        }
    }

    public Optional<Operation> get(Long id) throws Exception {
        Optional<Operation> value = this.operationRepository.findById(id);
        return Optional.ofNullable(value.orElseThrow(() -> new Exception("Id is wrong!")));
    }


}
