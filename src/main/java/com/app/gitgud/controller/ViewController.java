package com.app.gitgud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);

        return "home";
    }

    @GetMapping("/tutorials")
    public String tutorials(HttpSession session) {

        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        return "tutorials";
    }

    @GetMapping("/statistics")
    public String statistics(HttpSession session) {

        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        return "statistics";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        return "login";
    }

    @GetMapping("/test-login")
    public String testLogin(HttpSession session) {

        session.setAttribute("username", "Elenji");

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/home";
    }
}