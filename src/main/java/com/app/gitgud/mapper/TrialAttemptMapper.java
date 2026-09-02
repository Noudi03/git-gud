package com.app.gitgud.mapper;

import com.app.gitgud.model.TrialAttempt;
import com.app.gitgud.model.dto.TrialAttemptRequestDto;
import org.springframework.stereotype.Component;

@Component
public class TrialAttemptMapper {

    public TrialAttempt toModel(
            TrialAttemptRequestDto request
    ) {

        return new TrialAttempt(
                request.getTrialId(),
                request.isCorrect(),
                request.getTimeSpentMillis(),
                request.getHintsUsed(),
                request.getSkipCount()
        );
    }
}
