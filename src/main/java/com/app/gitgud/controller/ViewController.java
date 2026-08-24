package com.app.gitgud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("name", "Elenji");

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}