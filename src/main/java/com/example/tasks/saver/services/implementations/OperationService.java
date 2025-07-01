package com.example.tasks.saver.services.implementations;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import com.example.tasks.saver.repositories.OperationRepository;
import com.example.tasks.saver.services.interfaces.OperationServiceInterface;
import com.example.tasks.saver.utils.JsonUtils;
import jakarta.validation.constraints.NotNull;
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
@Service("operationService")
@Transactional
public class OperationService implements OperationServiceInterface {
    private final OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public static OperationService createOperationService(OperationRepository operationRepository) {
        return new OperationService(operationRepository);
    }

/*
    public List<Operation> listAll() {
        return new ArrayList<>(this.operationRepository
                .findAll());
    }
*/

    public Operation save(Task task, Operation operation) {
        if (operation != null) {
            if (log.isDebugEnabled()) {
                log.info(JsonUtils.toJson(operation));
            }
        } else {
            log.error("Operation not filled.");
            return null;
        }
        Optional<Operation> operationOptional = this.operationRepository.findByTaskNameAndOperationName(
                task.getTaskName(), operation.getOperationName());
        if (operationOptional.isPresent()) {
            log.info("Saving of operation = {}", operationOptional);
            if (operation.equals(operationOptional.get())) {
                return operationOptional.get();
            } else {
                log.info("Operation {} already exist ", operation.getOperationName());

                Operation realOperation = operationOptional.get();
                Operation updated = Operation.builder()
                        .id(realOperation.getId())
                        //.operationNumber(realOperation.getOperationNumber())
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

    private boolean existsById(Long id) {
        return this.operationRepository.existsById(id);
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void delete(long id) {
        if (!existsById(id)) {
            throw new NoSuchElementException();
        } else {
            this.operationRepository.deleteById(id);
        }
    }

/*
    public Optional<Operation> findByOperationNumber(Long operationNumber) {
        return this.operationRepository.findByOperationNumber(operationNumber);
    }
*/

/*
    public Optional<Operation> findByTaskNameAndOperationName(String taskName, String operationName) {
        return this.operationRepository.findByTaskNameAndOperationName(taskName, operationName);
    }
    public Optional<Operation> findByOperationName(String operationName){
        return this.operationRepository.findByOperationName(operationName);
    }
*/

/*
    public List<Operation> findSortAll(@NotNull Sort sort) {
        return this.operationRepository.findSortAll(sort);
    }

    public Page<Operation> findAllPages(@NotNull Pageable pageable) {
        return this.operationRepository.findAllPages(pageable);
    }
*/

/*
    @Override
    public List<Operation> findByTaskName(String taskName) {
        List<Operation> operationList = listAll();
        return operationList.stream().filter(op -> op.getTaskName().equals(taskName))
                .distinct()
                .sorted()
                .toList();

    }
*/

}
