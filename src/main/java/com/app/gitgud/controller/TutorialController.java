package com.app.gitgud.controller;

import com.app.gitgud.model.dto.TutorialPageDto;
import com.app.gitgud.service.TutorialPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TutorialController {

    private final TutorialPageService tutorialPageService;

    public TutorialController(
            TutorialPageService tutorialPageService
    ) {
        this.tutorialPageService =
                tutorialPageService;
    }

    @GetMapping("/tutorials")
    public String tutorials(
            Model model
    ) throws Exception {

        TutorialPageDto page =
                tutorialPageService.getTutorialPage();

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

        TutorialPageDto page =
                tutorialPageService.getTutorialPage(
                        chapterId
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

        TutorialPageDto page =
                tutorialPageService.getTutorialPage(
                        chapterId,
                        sectionId
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
}
