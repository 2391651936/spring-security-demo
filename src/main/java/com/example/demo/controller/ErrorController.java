package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/403.ftl")
    public String error403() {
        return "403";
    }

    @GetMapping("/404.ftl")
    public String error404() {
        return "404";
    }

    @GetMapping("/500.ftl")
    public String error500() {
        return "500";
    }
}
