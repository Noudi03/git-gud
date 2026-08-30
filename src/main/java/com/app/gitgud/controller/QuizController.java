package com.app.gitgud.controller;

import com.app.gitgud.model.dto.QuizPageDto;
import com.app.gitgud.service.QuizPageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    private final QuizPageService quizPageService;


    public QuizController(
            QuizPageService quizPageService
    ) {

        this.quizPageService =
                quizPageService;
    }


    /*
     * Section quiz
     */

    @GetMapping(
            "/api/quizzes/sections/{chapterId}/{sectionId}/{quizId}"
    )
    public QuizPageDto getSectionQuiz(
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            @PathVariable String quizId
    ) throws Exception {

        QuizPageDto page =
                quizPageService.getSectionQuiz(
                        chapterId,
                        sectionId,
                        quizId
                );

        if (page == null) {
            throw new IllegalArgumentException(
                    "Quiz not found"
            );
        }

        return page;
    }


    /*
     * Chapter quiz
     */

    @GetMapping(
            "/api/quizzes/chapters/{chapterId}/{quizId}"
    )
    public QuizPageDto getChapterQuiz(
            @PathVariable String chapterId,
            @PathVariable String quizId
    ) throws Exception {

        QuizPageDto page =
                quizPageService.getChapterQuiz(
                        chapterId,
                        quizId
                );

        if (page == null) {
            throw new IllegalArgumentException(
                    "Quiz not found"
            );
        }

        return page;
    }


    /*
     * Global quiz
     */

    @GetMapping(
            "/api/quizzes/{quizId}"
    )
    public QuizPageDto getGlobalQuiz(
            @PathVariable String quizId
    ) throws Exception {

        QuizPageDto page =
                quizPageService.getGlobalQuiz(
                        quizId
                );

        if (page == null) {
            throw new IllegalArgumentException(
                    "Quiz not found"
            );
        }

        return page;
    }
}
