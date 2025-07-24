package com.example.tasks.saver.rest;

import com.example.tasks.saver.dto.Operation;
import com.example.tasks.saver.dto.Task;
import com.example.tasks.saver.repositories.OperationRepository;
import com.example.tasks.saver.repositories.TaskRepository;
import com.example.tasks.saver.services.implementations.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.example.tasks.saver.global.InstallConstants.*;
import static com.example.tasks.saver.utils.JsonUtils.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@RequestMapping("/rest/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(OperationRepository operationRepository, TaskRepository taskRepository) {
        this.taskService = new TaskService(taskRepository, operationRepository);
    }

    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    public ModelAndView listAll(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Task> tasksPage = this.taskService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        List<Task> tasks = tasksPage.getContent();
        ModelAndView model = new ModelAndView(TASKS_PAGE);
        model.addObject("metaTitle", "Tasks List.");
        model.addObject("tasks", tasksPage.getContent());
        model.addObject("currentPage", currentPage);
        model.addObject("pageSize", pageSize);
        model.addObject("tasksPage", tasksPage);
        model.addObject("pageNumbers", tasksPage.getTotalPages());
        int totalPages = tasksPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(java.util.stream.Collectors.toList());
            model.addObject("pageNumbers", pageNumbers);
        }
        try {
            tasks.forEach(tsk -> log.info(toJson(tsk)));
            return model;
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView modelErr = new ModelAndView(ERR_PAGE);
            modelErr.addObject("title", ERR_PAGE_TITLE);
            modelErr.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return modelErr;
        }
    }

    @GetMapping(value = "/list/operations/{taskId}", produces = APPLICATION_JSON_VALUE)
    public ModelAndView listFilteredOperationsAllForTask(@PathVariable("taskId") Long taskId) throws Exception {
        Task realTask = this.taskService.get(taskId).orElseThrow(() -> new Exception("Id is wrong!"));
        List<Operation> operationsForTask = this.taskService.getOperationsByTaskId(taskId);
        ModelAndView modelAndView = new ModelAndView(TASK_OPERATIONS_PAGE);
        realTask = this.taskService.fillTaskByDefault(Optional.of(realTask));
        modelAndView.addObject("taskId", taskId);
        modelAndView.addObject("task", realTask);
        modelAndView.addObject("operationsForTask", operationsForTask);
        return modelAndView;
    }


    @GetMapping(value = "/new")
    public String showNewTaskPage(Model model, @RequestBody Optional<Task> taskRequest) {
        taskRequest = Optional.of(this.taskService.fillTaskByDefault(taskRequest));
        model.addAttribute("task", taskRequest.get());
        return NEW_TASK;
    }

    @PostMapping(value = "/save")
    public ModelAndView saveTask(@ModelAttribute("task") Task task) {
        this.taskService.save(task);
        return listAll(Optional.of(1), Optional.of(5));
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView editTaskById(@PathVariable String id) throws Exception {
        Task realTask = this.taskService.get(Long.valueOf(id)).orElseThrow(() -> new Exception("Id is wrong!"));
        ModelAndView modelAndView = new ModelAndView(EDIT_TASK);
        realTask = this.taskService.fillTaskByDefault(Optional.of(realTask));
        List<Operation> taskOperationsList = this.taskService.getOperationsByTaskId(Long.valueOf(id));
        modelAndView.addObject("task", realTask);
        modelAndView.addObject("taskOperationsList", taskOperationsList);
        modelAndView.addObject("taskOperationsListUrl", TASK_OPERATIONS_URL + id);
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    public ModelAndView save(@ModelAttribute("task") Task task) {

        ModelAndView modelAndView = new ModelAndView(EDIT_TASK);
        modelAndView.addObject("task", task);
        try {
            this.taskService.save(task);
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView model = new ModelAndView(ERR_PAGE);
            model.addObject("title", ERR_PAGE_TITLE);
            model.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return model;
        }
        return listAll(Optional.of(1), Optional.of(5));
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteTask(@PathVariable(name = "id") Long id) {
        this.taskService.delete(id);
        return listAll(Optional.of(1), Optional.of(5));
    }

}
