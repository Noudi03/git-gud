package com.app.gitgud.controller;

import com.app.gitgud.model.User;
import com.app.gitgud.service.CurrentUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final CurrentUserService currentUserService;


    public ViewController(
            CurrentUserService currentUserService
    ) {

        this.currentUserService =
                currentUserService;
    }


    @GetMapping("/")
    public String index() {

        return "redirect:/home";
    }


    @GetMapping("/home")
    public String home(
            Model model
    ) throws Exception {

        User user =
                currentUserService.getUser();

        model.addAttribute(
                "email",
                user.getEmail()
        );

        model.addAttribute(
                "username",
                user.getUsername()
        );

        return "home";
    }


    @GetMapping("/statistics")
    public String statistics() {

        return "statistics";
    }


    @GetMapping("/register")
    public String register() {

        return "register";
    }


    @GetMapping("/login")
    public String login() {

        return "login";
    }
}
