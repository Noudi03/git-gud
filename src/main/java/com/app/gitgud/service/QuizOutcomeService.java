package com.app.gitgud.service;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizResult;
import org.springframework.stereotype.Service;

@Service
public class QuizOutcomeService {

    private final QuizResultService quizResultService;
    private final RatingService ratingService;


    public QuizOutcomeService(
            QuizResultService quizResultService,
            RatingService ratingService
    ) {

        this.quizResultService =
                quizResultService;

        this.ratingService =
                ratingService;
    }


    public QuizResult process(
            String userId,
            QuizAttempt attempt,
            Quiz quiz
    ) throws Exception {

        QuizResult result =
                quizResultService.getResult(
                        userId,
                        attempt.getId()
                );

        ratingService.applyRating(
                userId,
                attempt,
                quiz,
                result
        );

        return result;
    }
}
