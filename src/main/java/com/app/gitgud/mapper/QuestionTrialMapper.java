package com.app.gitgud.mapper;

import com.app.gitgud.model.QuestionTrial;
import com.app.gitgud.model.dto.QuestionTrialDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionTrialMapper {

    public QuestionTrialDto toDto(
            QuestionTrial trial
    ) {

        if (trial == null) {
            return null;
        }

        return new QuestionTrialDto(
                trial.getId(),
                trial.getText(),
                trial.getOptions(),
                trial.getCorrectAnswer(),
                trial.getDifficulty(),
                trial.getHints(),
                trial.getOrder()
        );
    }
}
