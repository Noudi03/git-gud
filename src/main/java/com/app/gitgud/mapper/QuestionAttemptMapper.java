package com.app.gitgud.mapper;

import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.dto.QuestionAttemptRequestDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionAttemptMapper {

    public QuestionAttempt toModel(
            String id,
            QuestionAttemptRequestDto request,
            boolean correct
    ) {
        return new QuestionAttempt(
                id,
                request.getQuestionId(),
                correct,
                request.getTimeSpentMillis(),
                request.getHintsUsed(),
                request.getSkipCount()
        );
    }
}
