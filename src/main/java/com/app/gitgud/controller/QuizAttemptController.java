package com.app.gitgud.controller;

import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.QuizAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-attempts")
public class QuizAttemptController {

    private final QuizAttemptService quizAttemptService;
    private final CurrentUserService currentUserService;

    public QuizAttemptController(
            QuizAttemptService quizAttemptService,
            CurrentUserService currentUserService
    ) {
        this.quizAttemptService =
                quizAttemptService;

        this.currentUserService =
                currentUserService;
    }

    @PostMapping
    public ResponseEntity<?> createAttempt(
            @RequestBody QuizAttemptRequestDto request
    ) {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {
            QuizAttempt attempt =
                    quizAttemptService.createAttempt(
                            userId,
                            request
                    );

            return ResponseEntity.ok(
                    attempt
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not create quiz attempt"
                    );
        }
    }

    @GetMapping
    public ResponseEntity<?> getAttempts() {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {
            List<QuizAttempt> attempts =
                    quizAttemptService.getAttempts(
                            userId
                    );

            return ResponseEntity.ok(
                    attempts
            );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not get quiz attempts"
                    );
        }
    }

    @GetMapping("/{attemptId}")
    public ResponseEntity<?> getAttempt(
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
            QuizAttempt attempt =
                    quizAttemptService.getAttempt(
                            userId,
                            attemptId
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
                            "Could not get quiz attempt"
                    );
        }
    }
}
