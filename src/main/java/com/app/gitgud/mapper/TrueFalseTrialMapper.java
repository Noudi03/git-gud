package com.app.gitgud.mapper;

import com.app.gitgud.model.TrueFalseTrial;
import com.app.gitgud.model.dto.TrueFalseTrialDto;
import org.springframework.stereotype.Component;

@Component
public class TrueFalseTrialMapper {

    public TrueFalseTrialDto toDto(
            TrueFalseTrial trial
    ) {

        if (trial == null) {
            return null;
        }

        return new TrueFalseTrialDto(
                trial.getId(),
                trial.getText(),
                trial.isCorrectAnswer(),
                trial.getDifficulty(),
                trial.getHints(),
                trial.getOrder()
        );
    }
}
