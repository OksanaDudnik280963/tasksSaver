package com.example.tasks.saver.rest;

import com.example.tasks.saver.dto.Task;
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

import static com.example.tasks.saver.utils.JsonUtils.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@RequestMapping("/rest/api/tasks")
public class TaskController {
    public static final String ERR_MSG = "errMsg";
    public static final String ERR_PAGE = "/errors/error";
    public static final String TASKS_PAGE = "/tasks/tasksList";
    public static final String EDIT_TASK = "/tasks/edit_task";
    public static final String NEW_TASK = "/tasks/new_task";
    public static final String REDIRECT = "redirect:";

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskService = new TaskService(taskRepository);
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    public ModelAndView listAll(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Task> tasksPage = this.taskService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        List<Task> tasks = (List<Task>) tasksPage.getContent();
        ModelAndView model = new ModelAndView(TASKS_PAGE);
        model.addObject("metaTitle", "Tasks List.");
        model.addObject("tasks", tasksPage.getContent());
        //modelAndView.addObject("tasks", tasksPage.getContent());
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
            modelErr.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return modelErr;
        }
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
        modelAndView.addObject("task", realTask);
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
