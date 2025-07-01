package com.example.tasks.saver.rest.global;


import com.example.tasks.saver.dto.enums.ItemNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping(value = {"/rest/api/home"})
public class HomeController {
    public static final String ERR_MSG = "errMsg";
    public static final String ERR_PAGE = "/errors/error";
    public static final String REDIRECT = "redirect:";
    public static final String VIEW_NAME = "viewName";

    @RequestMapping(value = {"/openItem"})
    public String root(Locale locale, ModelMap model) {
        model.addAttribute("content", "content");

        List<String> items = new ArrayList<>();
        items.add(ItemNames.IASKS_LIST.name());
        items.add(ItemNames.CREATION_TASK.name());
        items.add(ItemNames.OPERATIONS_LIST.name());
        items.add(ItemNames.CREATION_OPERATION.name());
        items.add(ItemNames.CALCULATE_TASK_COST.name());

        model.addAttribute("items", items);
        return "home";
    }
}
