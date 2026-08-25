package com.app.gitgud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("name", "User");

        return "home";
    }

    @GetMapping("/tutorials")
    public String tutorials() {
        return "tutorials";
    }

    @GetMapping("/statistics")
    public String statistics() {
        return "statistics";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}