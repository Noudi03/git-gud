package com.app.gitgud.mapper;

import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.dto.QuestionAttemptRequestDto;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;
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
                ),
                request.getQuestionAttempts()
                        .stream()
                        .map(this::toModel)
                        .toList()
        );
    }

    private QuestionAttempt toModel(
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
