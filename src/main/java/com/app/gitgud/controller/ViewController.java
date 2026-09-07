package com.app.gitgud.controller;

import com.app.gitgud.model.User;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.selection.UserTierResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final CurrentUserService currentUserService;
    private final UserTierResolver userTierResolver;

    public ViewController(
            CurrentUserService currentUserService,
            UserTierResolver userTierResolver
    ) {
        this.currentUserService =
                currentUserService;

        this.userTierResolver =
                userTierResolver;
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

        model.addAttribute(
                "rating",
                user.getRating()
        );

        model.addAttribute(
                "tier",
                userTierResolver
                        .resolve(user)
                        .getValue()
        );

        return "home";
    }

    @GetMapping("/statistics")
    public String statistics() {

        return "statistics";
    }

    @GetMapping("/leaderboards")
    public String leaderboards() {

        return "leaderboards";
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
