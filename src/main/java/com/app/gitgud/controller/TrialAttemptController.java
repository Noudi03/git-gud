package com.app.gitgud.controller;

import com.app.gitgud.model.TrialAttempt;
import com.app.gitgud.model.dto.TrialAttemptRequestDto;
import com.app.gitgud.service.CurrentUserService;
import com.app.gitgud.service.TrialAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        "/api/quiz-attempts/{attemptId}/trial-attempts"
)
public class TrialAttemptController {

    private final TrialAttemptService trialAttemptService;
    private final CurrentUserService currentUserService;

    public TrialAttemptController(
            TrialAttemptService trialAttemptService,
            CurrentUserService currentUserService
    ) {

        this.trialAttemptService =
                trialAttemptService;

        this.currentUserService =
                currentUserService;
    }

    @PostMapping
    public ResponseEntity<?> createTrialAttempt(
            @PathVariable String attemptId,
            @RequestBody TrialAttemptRequestDto request
    ) {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {

            trialAttemptService.createTrialAttempt(
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
                            "Could not create trial attempt"
                    );
        }
    }

    @GetMapping
    public ResponseEntity<?> getTrialAttempts(
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

            List<TrialAttempt> attempts =
                    trialAttemptService
                            .getTrialAttempts(
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
                            "Could not get trial attempts"
                    );
        }
    }

    @GetMapping("/{trialId}")
    public ResponseEntity<?> getTrialAttempt(
            @PathVariable String attemptId,
            @PathVariable String trialId
    ) {

        String userId =
                currentUserService.getUserId();

        if (userId == null) {
            return ResponseEntity
                    .status(401)
                    .body("Not authenticated");
        }

        try {

            TrialAttempt attempt =
                    trialAttemptService
                            .getTrialAttempt(
                                    userId,
                                    attemptId,
                                    trialId
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
                            "Could not get trial attempt"
                    );
        }
    }
}
