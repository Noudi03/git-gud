package com.app.gitgud.controller;

import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.dto.QuestionAttemptRequestDto;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.QuizAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

        try {

            String userId =
                    currentUserService.getUserId();


            if (userId == null) {

                return ResponseEntity
                        .status(401)
                        .body("Not authenticated");
            }


            String attemptId =
                    UUID.randomUUID().toString();


            QuizAttemptStatus status =
                    QuizAttemptStatus.fromValue(
                            request.getStatus()
                    );


            List<QuestionAttempt> questionAttempts =
                    request.getQuestionAttempts()
                            .stream()
                            .map(
                                    this::toQuestionAttempt
                            )
                            .toList();


            QuizAttempt attempt =
                    new QuizAttempt(
                            attemptId,
                            request.getQuizId(),
                            status,
                            questionAttempts
                    );


            quizAttemptService.createAttempt(
                    userId,
                    attempt
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
                            "Could not create quiz attempt"
                    );
        }
    }


    private QuestionAttempt toQuestionAttempt(
            QuestionAttemptRequestDto request
    ) {

        return new QuestionAttempt(
                request.getQuestionId(),
                request.isCorrect(),
                request.getTimeSpentMillis(),
                request.getHintsUsed(),
                request.getSkipCount()
        );
    }
}
