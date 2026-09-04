package com.app.gitgud.mapper;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import org.springframework.stereotype.Component;

@Component
public class QuizAttemptMapper {

    public QuizAttempt toModel(
            String id,
            Quiz quiz,
            QuizAttemptRequestDto request
    ) {
        return new QuizAttempt(
                id,
                quiz.getId(),
                QuizAttemptStatus.fromValue(
                        request.getStatus()
                ),
                quiz.getContext()
        );
    }
}
