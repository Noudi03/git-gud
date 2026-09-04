package com.app.gitgud.service;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizResult;
import org.springframework.stereotype.Service;

@Service
public class QuizOutcomeService {

    private final QuizResultService quizResultService;
    private final RatingService ratingService;
    private final CurriculumAccessService curriculumAccessService;


    public QuizOutcomeService(
            QuizResultService quizResultService,
            RatingService ratingService,
            CurriculumAccessService curriculumAccessService
    ) {

        this.quizResultService =
                quizResultService;

        this.ratingService =
                ratingService;

        this.curriculumAccessService =
                curriculumAccessService;
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


    public boolean isQuizPassed(
            String userId,
            Quiz quiz
    ) throws Exception {

        return curriculumAccessService.hasPassedQuiz(
                userId,
                quiz
        );
    }
}
