package com.app.gitgud.controller;

import com.app.gitgud.model.dto.QuizPageDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.QuizPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizPageService quizPageService;
    private final CurrentUserService currentUserService;

    public QuizController(
            QuizPageService quizPageService,
            CurrentUserService currentUserService
    ) {

        this.quizPageService =
                quizPageService;

        this.currentUserService =
                currentUserService;
    }

    @GetMapping(
            "/sections/{chapterId}/{sectionId}/{quizId}"
    )
    public ResponseEntity<?> getSectionQuiz(
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            @PathVariable String quizId
    ) {

        try {

            QuizPageDto page =
                    quizPageService.getSectionQuiz(
                            chapterId,
                            sectionId,
                            quizId,
                            currentUserService.getUser()
                    );

            if (page == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }

            return ResponseEntity.ok(page);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Could not get quiz");
        }
    }

    @GetMapping(
            "/chapters/{chapterId}/{quizId}"
    )
    public ResponseEntity<?> getChapterQuiz(
            @PathVariable String chapterId,
            @PathVariable String quizId
    ) {

        try {

            QuizPageDto page =
                    quizPageService.getChapterQuiz(
                            chapterId,
                            quizId,
                            currentUserService.getUser()
                    );

            if (page == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }

            return ResponseEntity.ok(page);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Could not get quiz");
        }
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getGlobalQuiz(
            @PathVariable String quizId
    ) {

        try {

            QuizPageDto page =
                    quizPageService.getGlobalQuiz(
                            quizId,
                            currentUserService.getUser()
                    );

            if (page == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }

            return ResponseEntity.ok(page);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Could not get quiz");
        }
    }
}
