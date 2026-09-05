package com.app.gitgud.mapper;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import org.springframework.stereotype.Component;

@Component
public class QuizAttemptMapper {

    public QuizAttempt toModel(
            String id,
            Quiz quiz
    ) {

        return new QuizAttempt(
                id,
                quiz.getId(),
                QuizAttemptStatus.IN_PROGRESS,
                quiz.getContext()
        );
    }
}
