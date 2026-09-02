package com.app.gitgud.mapper;

import com.app.gitgud.model.QuestionTrial;
import com.app.gitgud.model.Trial;
import com.app.gitgud.model.dto.TrialDto;
import org.springframework.stereotype.Component;

@Component
public class TrialMapper {

    private final QuestionTrialMapper questionTrialMapper;

    public TrialMapper(
            QuestionTrialMapper questionTrialMapper
    ) {

        this.questionTrialMapper =
                questionTrialMapper;
    }

    public TrialDto toDto(
            Trial trial
    ) {

        if (trial == null) {
            return null;
        }

        return switch (trial.getType()) {

            case QUESTION ->
                    questionTrialMapper.toDto(
                            (QuestionTrial) trial
                    );

            case FILL_IN_THE_BLANK ->
                    throw new UnsupportedOperationException(
                            "Fill-in-the-blank trials are not implemented yet"
                    );
        };
    }
}
