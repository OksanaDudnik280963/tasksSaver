package com.example.tasks.saver.rest.error;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping(value = "/rest/api/tasks/errors")
public class IssueController implements org.springframework.boot.web.servlet.error.ErrorController {

    public String getErrorPath() {
        return null;
    }

    private static final String PATH = "/";

    @GetMapping(value = PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void error(HttpServletResponse response) throws IOException {
        int status = response.getStatus();
        switch (status) {
            case 404:
                response.sendRedirect("/404");
                break;
            case 401:
                response.sendRedirect("/401");
                break;
            case 403:
                response.sendRedirect("/403");
                break;
            case 405:
                response.sendRedirect("/405");
                break;

            default:
                response.sendRedirect("/error");
        }
    }
    @GetMapping("/404")
    String get404ErrorPage() {
        return "/errors/404";
    }

    @GetMapping("/403")
    String get403ErrorPage() {
        return "/errors/403";
    }

    @GetMapping("/401")
    String get401ErrorPage() {
        return "/errors/401";
    }

    @GetMapping("/405")
    String get405ErrorPage() {
        return "/errors/405";
    }

    @GetMapping("/error")
    String getErrorPage() {
        return "/errors/error";
    }

}
