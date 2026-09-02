package com.app.gitgud.mapper;

import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QuizAttemptMapper {

    public QuizAttempt toModel(
            QuizAttemptRequestDto request
    ) {

        return new QuizAttempt(
                UUID.randomUUID().toString(),
                request.getQuizId(),
                QuizAttemptStatus.fromValue(
                        request.getStatus()
                )
        );
    }
}
