package com.app.gitgud.mapper;

import com.app.gitgud.model.FillInTheBlankTrial;
import com.app.gitgud.model.dto.FillInTheBlankTrialDto;
import org.springframework.stereotype.Component;

@Component
public class FillInTheBlankTrialMapper {

    public FillInTheBlankTrialDto toDto(
            FillInTheBlankTrial trial
    ) {

        if (trial == null) {
            return null;
        }

        return new FillInTheBlankTrialDto(
                trial.getId(),
                trial.getText(),
                trial.getCorrectAnswer(),
                trial.getDifficulty(),
                trial.getHints(),
                trial.getOrder()
        );
    }
}
