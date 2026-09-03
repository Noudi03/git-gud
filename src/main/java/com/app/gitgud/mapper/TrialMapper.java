package com.app.gitgud.mapper;

import com.app.gitgud.model.FillInTheBlankTrial;
import com.app.gitgud.model.QuestionTrial;
import com.app.gitgud.model.TrueFalseTrial;
import com.app.gitgud.model.Trial;
import com.app.gitgud.model.dto.TrialDto;
import org.springframework.stereotype.Component;

@Component
public class TrialMapper {

    private final QuestionTrialMapper questionTrialMapper;
    private final FillInTheBlankTrialMapper fillInTheBlankTrialMapper;
    private final TrueFalseTrialMapper trueFalseTrialMapper;

    public TrialMapper(
            QuestionTrialMapper questionTrialMapper,
            FillInTheBlankTrialMapper fillInTheBlankTrialMapper,
            TrueFalseTrialMapper trueFalseTrialMapper
    ) {

        this.questionTrialMapper =
                questionTrialMapper;

        this.fillInTheBlankTrialMapper =
                fillInTheBlankTrialMapper;

        this.trueFalseTrialMapper =
                trueFalseTrialMapper;
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
                    fillInTheBlankTrialMapper.toDto(
                            (FillInTheBlankTrial) trial
                    );

            case TRUE_FALSE ->
                    trueFalseTrialMapper.toDto(
                            (TrueFalseTrial) trial
                    );
        };
    }
}
