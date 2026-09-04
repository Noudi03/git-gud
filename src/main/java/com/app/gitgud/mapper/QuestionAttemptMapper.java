package com.app.gitgud.mapper;

import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.dto.QuestionAttemptRequestDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionAttemptMapper {

    public QuestionAttempt toModel(
            String id,
            QuestionAttemptRequestDto request
    ) {

        return new QuestionAttempt(
                id,
                request.getQuestionId(),
                request.isCorrect(),
                request.getTimeSpentMillis(),
                request.getHintsUsed(),
                request.getSkipCount()
        );
    }
}
