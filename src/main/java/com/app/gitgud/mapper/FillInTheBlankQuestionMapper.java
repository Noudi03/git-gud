package com.app.gitgud.mapper;

import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.dto.FillInTheBlankQuestionDto;
import org.springframework.stereotype.Component;

@Component
public class FillInTheBlankQuestionMapper {

    public FillInTheBlankQuestionDto toDto(
            FillInTheBlankQuestion question
    ) {

        if (question == null) {
            return null;
        }

        return new FillInTheBlankQuestionDto(
                question.getId(),
                question.getText(),
                question.getCorrectAnswer(),
                question.getDifficulty(),
                question.getHints(),
                question.getOrder()
        );
    }
}
