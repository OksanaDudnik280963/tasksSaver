package com.example.tasks.saver.rest;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import com.example.tasks.saver.repositories.OperationRepository;
import com.example.tasks.saver.services.implementations.OperationService;
import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.tasks.saver.rest.global.HomeController.*;
import static com.example.tasks.saver.utils.JsonUtils.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/rest/api/tasks/operations")
public class OperationController {
    private final OperationRepository operationRepository;

    //@Autowired
    private final OperationService operationService;
    @Autowired
    public OperationController(OperationRepository operationRepository) {
        this.operationService = new OperationService(operationRepository);
                //OperationService.createOperationService(operationRepository);
        this.operationRepository = operationRepository;
    }

    @GetMapping(value = "/", produces = {APPLICATION_JSON_VALUE})
    public String listAll(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Operation> operations = this.operationRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("operations", operations);
        model.addAttribute("currentPage", page);
        model.addAttribute(VIEW_NAME, "/operations/operationsList");

        try {
            operations.forEach(operation ->
                    log.info(toJson(operation))
            );
            return (String) model.getAttribute(VIEW_NAME);
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            model.addAttribute(ERR_MSG, exMessage);
            model.addAttribute(VIEW_NAME, ERR_PAGE);
            log.info(exMessage);
            return REDIRECT + ERR_PAGE;
        }
    }

    @GetMapping(value = "/new", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String showNewOperationPage(Model model) {
        Operation operation = new Operation();
        String taskName = (String) model.getAttribute("task");
        if (StringUtils.isBlank(taskName)) {
            operation.setTaskName(taskName);
        }
        model.addAttribute("operation", operation);
        return REDIRECT + "/operations/new_operation";
    }

    @PostMapping(value = "/save", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String saveOperation(@ModelAttribute("task") Task task, @ModelAttribute("operation") Operation operation) {
        this.operationService.save(task, operation);
        return "/operations/edit_operation";
    }

    @DeleteMapping(value = "/delete/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String deleteTask(@PathVariable(name = "id") Long id) {
        this.operationService.delete(id);
        return "redirect:/operations/operationsList";
    }

}
