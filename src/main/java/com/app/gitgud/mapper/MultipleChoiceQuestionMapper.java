package com.app.gitgud.mapper;

import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.dto.MultipleChoiceQuestionDto;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceQuestionMapper {

    public MultipleChoiceQuestionDto toDto(
            MultipleChoiceQuestion question
    ) {

        if (question == null) {
            return null;
        }

        return new MultipleChoiceQuestionDto(
                question.getId(),
                question.getText(),
                question.getOptions(),
                question.getCorrectAnswer(),
                question.getDifficulty(),
                question.getHints()
        );
    }
}
