package com.app.gitgud.controller;

import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.QuizAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-attempts")
public class QuizAttemptController {

    private final QuizAttemptService quizAttemptService;
    private final CurrentUserService currentUserService;

    public QuizAttemptController(
            QuizAttemptService quizAttemptService,
            CurrentUserService currentUserService
    ) {
        this.quizAttemptService = quizAttemptService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<?> createAttempt(
            @RequestBody QuizAttemptRequestDto request
    ) {
        try {
            String userId = currentUserService.getUserId();

            if (userId == null) {
                return ResponseEntity
                        .status(401)
                        .body("Not authenticated");
            }

            quizAttemptService.createAttempt(
                    userId,
                    request
            );

            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Could not create quiz attempt");
        }
    }
}
