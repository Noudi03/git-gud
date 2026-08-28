package com.app.gitgud.controller;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Section;
import com.app.gitgud.service.TutorialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class TutorialController {

    private final TutorialService tutorialService;

    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping("/tutorials")
    public String tutorials(Model model) throws Exception {

        addTutorialNavigation(model);

        return "tutorials";
    }

    @GetMapping("/tutorials/{chapterId}/{sectionId}")
    public String section(
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            Model model
    ) throws Exception {

        addTutorialNavigation(model);

        Chapter chapter =
                tutorialService.getTutorialChapter(chapterId);

        if (chapter == null) {
            return "redirect:/tutorials";
        }

        Section section =
                tutorialService.getTutorialSection(
                        chapterId,
                        sectionId
                );

        if (section == null) {
            return "redirect:/tutorials";
        }

        model.addAttribute("chapter", chapter);
        model.addAttribute("section", section);

        return "tutorials";
    }

    private void addTutorialNavigation(Model model)
            throws Exception {

        List<Chapter> chapters =
                tutorialService.getTutorialChapters();

        Map<String, List<Section>> sectionsByChapter =
                tutorialService.getTutorialNavigation(chapters);

        model.addAttribute("chapters", chapters);
        model.addAttribute(
                "sectionsByChapter",
                sectionsByChapter
        );
    }
}
