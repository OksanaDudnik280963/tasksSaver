package com.example.tasks.saver.rest.global;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;

import static com.example.tasks.saver.global.InstallConstants.PATH_ERROR;

@Controller
@RequestMapping(value = "/rest/api/tasks/errors")
public class GlobalErrorController implements ErrorController {


    @RequestMapping(value = PATH_ERROR)
    public void error(HttpServletResponse response) throws IOException {
        int status = response.getStatus();
        switch (status) {
            case 401:
                response.sendRedirect("/401");
                break;
            case 403:
                response.sendRedirect("/403");
                break;
            default:
                response.sendRedirect("/404");
        }

    }
    @RequestMapping("404")
    String get404ErrorPage() {
        return "404";
    }

    @RequestMapping("401")
    String get401ErrorPage() {
        return "401";
    }

    @RequestMapping("403")
    String get403ErrorPage() {
        return "403";
    }

}
