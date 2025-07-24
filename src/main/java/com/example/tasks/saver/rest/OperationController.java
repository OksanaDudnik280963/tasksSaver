package com.example.tasks.saver.rest;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.repositories.OperationRepository;
import com.example.tasks.saver.repositories.TaskRepository;
import com.example.tasks.saver.services.implementations.OperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.tasks.saver.global.InstallConstants.*;
import static com.example.tasks.saver.utils.JsonUtils.toJson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/rest/api/tasks/operations")
public class OperationController {

    private OperationService operationService;

    @Autowired
    private OperationService operationService(OperationRepository operationRepository, TaskRepository taskRepository) {
        this.operationService = new OperationService(operationRepository, taskRepository);
        return this.operationService;
    }


    @GetMapping(value = "/list")
    public ModelAndView listAll(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Operation> operationsPage = this.operationService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        List<Operation> operations = (List<Operation>) operationsPage.getContent();
        ModelAndView model = new ModelAndView(LIST_OPERATIONS);
        model.addObject("metaTitle", "Tasks List.");
        model.addObject("operations", operationsPage.getContent());
        model.addObject("currentPage", currentPage);
        model.addObject("pageSize", pageSize);
        model.addObject("operationsPage", operationsPage);
        model.addObject("pageNumbers", operationsPage.getTotalPages());
        int totalPages = operationsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addObject("pageNumbers", pageNumbers);
        }
        try {
            operations.forEach(operation -> log.info(toJson(operation)));
            return model;
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView modelErr = new ModelAndView(ERR_PAGE);
            modelErr.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return modelErr;
        }
    }

    @GetMapping(value = "/new")
    public String showNewOperationPage(Model model, @RequestBody Optional<Operation> operationRequest) {
        operationRequest = Optional.of(this.operationService.fillOperationByDefault(operationRequest));
        model.addAttribute("operation", operationRequest.get());
        return NEW_OPERATION;
    }

    @PostMapping(value = "/save")
    public ModelAndView saveOperation(@ModelAttribute("operation") Operation operation) {
        this.operationService.save(operation);
        return listAll(Optional.of(1), Optional.of(5));
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView editOperationById(@PathVariable String id) throws Exception {
        Operation realOperation = this.operationService.get(Long.valueOf(id)).orElseThrow(() -> new Exception("Id is wrong!"));
        ModelAndView modelAndView = new ModelAndView(EDIT_OPERATION);
        realOperation = this.operationService.fillOperationByDefault(Optional.of(realOperation));
        modelAndView.addObject("operation", realOperation);
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    public ModelAndView editOperation(@ModelAttribute("operation") Operation operation) {

        ModelAndView modelAndView = new ModelAndView(EDIT_OPERATION);
        modelAndView.addObject("operation", operation);
        try {
            this.operationService.save(operation);
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView model = new ModelAndView(ERR_PAGE);
            model.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return model;
        }
        return listAll(Optional.of(1), Optional.of(5));
    }


    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteOperation(@PathVariable(name = "id") Long id) {
        this.operationService.delete(id);
        return listAll(Optional.of(1), Optional.of(5));
    }
}
