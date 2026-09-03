package com.app.gitgud.mapper;

import com.app.gitgud.model.TrueFalseQuestion;
import com.app.gitgud.model.dto.TrueFalseQuestionDto;
import org.springframework.stereotype.Component;

@Component
public class TrueFalseQuestionMapper {

    public TrueFalseQuestionDto toDto(
            TrueFalseQuestion question
    ) {

        if (question == null) {
            return null;
        }

        return new TrueFalseQuestionDto(
                question.getId(),
                question.getText(),
                question.isCorrectAnswer(),
                question.getDifficulty(),
                question.getHints(),
                question.getOrder()
        );
    }
}
