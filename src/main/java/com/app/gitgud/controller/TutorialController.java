package com.app.gitgud.controller;

import com.app.gitgud.model.User;
import com.app.gitgud.model.dto.TutorialPageDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.CurriculumAccessDeniedException;
import com.app.gitgud.service.TutorialPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TutorialController {

    private final TutorialPageService tutorialPageService;
    private final CurrentUserService currentUserService;

    public TutorialController(
            TutorialPageService tutorialPageService,
            CurrentUserService currentUserService
    ) {

        this.tutorialPageService =
                tutorialPageService;

        this.currentUserService =
                currentUserService;
    }

    @GetMapping("/tutorials")
    public String tutorials(
            Model model
    ) throws Exception {

        User user =
                currentUserService.getUser();

        TutorialPageDto page =
                tutorialPageService.getTutorialPage(
                        user
                );

        model.addAttribute(
                "page",
                page
        );

        return "tutorials";
    }

    @GetMapping("/tutorials/{chapterId}")
    public String chapter(
            @PathVariable String chapterId,
            Model model
    ) throws Exception {

        User user =
                currentUserService.getUser();

        TutorialPageDto page =
                tutorialPageService.getTutorialPage(
                        chapterId,
                        user
                );

        if (page == null) {
            return "redirect:/tutorials";
        }

        model.addAttribute(
                "page",
                page
        );

        return "tutorials";
    }

    @GetMapping(
            "/tutorials/{chapterId}/{sectionId}"
    )
    public String section(
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            Model model
    ) throws Exception {

        User user =
                currentUserService.getUser();

        TutorialPageDto page =
                tutorialPageService.getTutorialPage(
                        chapterId,
                        sectionId,
                        user
                );

        if (page == null) {
            return "redirect:/tutorials";
        }

        model.addAttribute(
                "page",
                page
        );

        return "tutorials";
    }

    @ExceptionHandler(
            CurriculumAccessDeniedException.class
    )
    public ResponseEntity<String> handleAccessDenied(
            CurriculumAccessDeniedException exception
    ) {

        return ResponseEntity
                .status(
                        HttpStatus.FORBIDDEN
                )
                .body(
                        exception.getMessage()
                );
    }
}
