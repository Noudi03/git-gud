package com.app.gitgud.controller;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizResult;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.QuizAccessService;
import com.app.gitgud.service.QuizAttemptService;
import com.app.gitgud.service.QuizOutcomeService;
import com.app.gitgud.service.QuizResultService;
import com.app.gitgud.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-attempts")
public class QuizAttemptController {

    private final QuizAttemptService quizAttemptService;
    private final QuizResultService quizResultService;
    private final QuizOutcomeService quizOutcomeService;
    private final QuizAccessService quizAccessService;
    private final QuizService quizService;
    private final CurrentUserService currentUserService;


    public QuizAttemptController(
            QuizAttemptService quizAttemptService,
            QuizResultService quizResultService,
            QuizOutcomeService quizOutcomeService,
            QuizAccessService quizAccessService,
            QuizService quizService,
            CurrentUserService currentUserService
    ) {

        this.quizAttemptService =
                quizAttemptService;

        this.quizResultService =
                quizResultService;

        this.quizOutcomeService =
                quizOutcomeService;

        this.quizAccessService =
                quizAccessService;

        this.quizService =
                quizService;

        this.currentUserService =
                currentUserService;
    }


    @PostMapping("/sections/{chapterId}/{sectionId}/{quizId}")
    public ResponseEntity<?> createSectionAttempt(
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            @PathVariable String quizId,
            @RequestBody QuizAttemptRequestDto request
    ) {

        return createAttempt(
                request,
                () -> quizService.getSectionQuiz(
                        chapterId,
                        sectionId,
                        quizId
                )
        );
    }


    @PostMapping("/chapters/{chapterId}/{quizId}")
    public ResponseEntity<?> createChapterAttempt(
            @PathVariable String chapterId,
            @PathVariable String quizId,
            @RequestBody QuizAttemptRequestDto request
    ) {

        return createAttempt(
                request,
                () -> quizService.getChapterQuiz(
                        chapterId,
                        quizId
                )
        );
    }


    @PostMapping("/{quizId}")
    public ResponseEntity<?> createGlobalAttempt(
            @PathVariable String quizId,
            @RequestBody QuizAttemptRequestDto request
    ) {

        return createAttempt(
                request,
                () -> quizService.getGlobalQuiz(
                        quizId
                )
        );
    }


    @PostMapping("/{attemptId}/finalize")
    public ResponseEntity<?> finalizeAttempt(
            @PathVariable String attemptId,
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

            QuizAttemptStatus status =
                    QuizAttemptStatus.fromValue(
                            request.getStatus()
                    );

            if (
                    status != QuizAttemptStatus.COMPLETED
                            && status != QuizAttemptStatus.CANCELLED
            ) {
                return ResponseEntity
                        .badRequest()
                        .body(
                                "Invalid final quiz attempt status"
                        );
            }

            QuizAttempt existingAttempt =
                    quizAttemptService.getAttempt(
                            userId,
                            attemptId
                    );

            Quiz quiz =
                    quizAttemptService.getQuiz(
                            existingAttempt
                    );

            QuizAttempt attempt =
                    quizAttemptService.finalizeAttempt(
                            userId,
                            attemptId,
                            status
                    );

            QuizResult result =
                    quizOutcomeService.process(
                            userId,
                            attempt,
                            quiz
                    );

            return ResponseEntity.ok(
                    result
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            e.getMessage()
                    );

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(409)
                    .body(
                            e.getMessage()
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not finalize quiz attempt"
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


    @GetMapping("/{attemptId}/result")
    public ResponseEntity<?> getResult(
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

            QuizResult result =
                    quizResultService.getResult(
                            userId,
                            attemptId
                    );

            return ResponseEntity.ok(
                    result
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .notFound()
                    .build();

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(409)
                    .body(
                            e.getMessage()
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not get quiz result"
                    );
        }
    }


    private ResponseEntity<?> createAttempt(
            QuizAttemptRequestDto request,
            QuizSupplier quizSupplier
    ) {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {

            Quiz quiz =
                    quizSupplier.get();

            quizAccessService.validateCanStartQuiz(
                    userId,
                    quiz
            );

            QuizAttempt attempt =
                    quizAttemptService.createAttempt(
                            userId,
                            quiz,
                            request
                    );

            return ResponseEntity.ok(
                    attempt
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            e.getMessage()
                    );

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(403)
                    .body(
                            e.getMessage()
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Could not create quiz attempt"
                    );
        }
    }


    @FunctionalInterface
    private interface QuizSupplier {
        Quiz get() throws Exception;
    }
}
