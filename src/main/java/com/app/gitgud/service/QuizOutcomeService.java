package com.app.gitgud.service;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizResult;
import com.app.gitgud.repository.FirestoreQuizOutcomeRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizOutcomeService {

    private final QuizResultService quizResultService;
    private final RatingService ratingService;
    private final FirestoreQuizOutcomeRepository outcomeRepository;

    public QuizOutcomeService(
            QuizResultService quizResultService,
            RatingService ratingService,
            FirestoreQuizOutcomeRepository outcomeRepository
    ) {
        this.quizResultService =
                quizResultService;

        this.ratingService =
                ratingService;

        this.outcomeRepository =
                outcomeRepository;
    }

    public QuizResult process(
            String userId,
            QuizAttempt attempt,
            Quiz quiz,
            QuizAttemptStatus finalStatus
    ) throws Exception {

        if (
                finalStatus != QuizAttemptStatus.COMPLETED
                        && finalStatus != QuizAttemptStatus.CANCELLED
        ) {
            throw new IllegalArgumentException(
                    "Invalid final quiz attempt status"
            );
        }

        if (
                attempt.getStatus()
                        != QuizAttemptStatus.IN_PROGRESS
        ) {
            throw new IllegalStateException(
                    "Quiz attempt is not in progress"
            );
        }

        QuizResult result =
                quizResultService
                        .calculateResultForFinalization(
                                userId,
                                attempt
                        );

        RatingService.RatingUpdate ratingUpdate =
                ratingService.calculateRatingUpdate(
                        userId,
                        attempt,
                        quiz,
                        result
                );

        outcomeRepository.finalizeAttempt(
                userId,
                attempt.getId(),
                finalStatus,
                ratingUpdate.getRatingChange(),
                ratingUpdate.getAbsoluteRating()
        );

        return result;
    }
}
