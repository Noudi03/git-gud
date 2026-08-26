package com.app.gitgud.controller;

import jakarta.servlet.http.HttpSession;
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
    public String home(HttpSession session, Model model) {

        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        model.addAttribute(
                "email",
                session.getAttribute("email")
        );

        model.addAttribute(
                "username",
                "mr.user"
        );

        return "home";
    }

    @GetMapping("/tutorials")
    public String tutorials(HttpSession session) {

        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        return "tutorials";
    }

    @GetMapping("/statistics")
    public String statistics(HttpSession session) {

        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

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

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("uid") != null;
    }
}
