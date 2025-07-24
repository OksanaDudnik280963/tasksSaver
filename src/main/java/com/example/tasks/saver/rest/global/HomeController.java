package com.example.tasks.saver.rest.global;


import com.example.tasks.saver.dto.enums.ItemNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.tasks.saver.global.InstallConstants.HOME_PAGE;

@Controller
@RequestMapping(value = {"/rest/api/tasks"})
public class HomeController {


    @RequestMapping(value = {"/home"})
    public String root(ModelMap model) {
        model.addAttribute("content", "content");
        ItemNames.Item.findAll();
        model.addAttribute("items", ItemNames.items);
        return HOME_PAGE;
    }
}
