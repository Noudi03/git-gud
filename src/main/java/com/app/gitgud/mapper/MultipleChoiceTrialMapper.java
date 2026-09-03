package com.app.gitgud.mapper;

import com.app.gitgud.model.MultipleChoiceTrial;
import com.app.gitgud.model.dto.MultipleChoiceTrialDto;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceTrialMapper {

    public MultipleChoiceTrialDto toDto(
            MultipleChoiceTrial trial
    ) {

        if (trial == null) {
            return null;
        }

        return new MultipleChoiceTrialDto(
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
