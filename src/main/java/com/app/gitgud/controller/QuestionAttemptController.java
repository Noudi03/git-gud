package com.app.gitgud.controller;

import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.dto.QuestionAttemptRequestDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.QuestionAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        "/api/quiz-attempts/{attemptId}/question-attempts"
)
public class QuestionAttemptController {

    private final QuestionAttemptService questionAttemptService;
    private final CurrentUserService currentUserService;

    public QuestionAttemptController(
            QuestionAttemptService questionAttemptService,
            CurrentUserService currentUserService
    ) {

        this.questionAttemptService =
                questionAttemptService;

        this.currentUserService =
                currentUserService;
    }

    @PostMapping
    public ResponseEntity<?> createQuestionAttempt(
            @PathVariable String attemptId,
            @RequestBody QuestionAttemptRequestDto request
    ) {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {

            questionAttemptService.createQuestionAttempt(
                    userId,
                    attemptId,
                    request
            );

            return ResponseEntity
                    .ok()
                    .build();

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not create question attempt"
                    );
        }
    }

    @GetMapping
    public ResponseEntity<?> getQuestionAttempts(
            @PathVariable String attemptId
    ) {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {

            List<QuestionAttempt> attempts =
                    questionAttemptService
                            .getQuestionAttempts(
                                    userId,
                                    attemptId
                            );

            return ResponseEntity.ok(
                    attempts
            );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not get question attempts"
                    );
        }
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestionAttempt(
            @PathVariable String attemptId,
            @PathVariable String questionId
    ) {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {

            QuestionAttempt attempt =
                    questionAttemptService
                            .getQuestionAttempt(
                                    userId,
                                    attemptId,
                                    questionId
                            );

            return ResponseEntity.ok(
                    attempt
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .notFound()
                    .build();

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not get question attempt"
                    );
        }
    }
}
