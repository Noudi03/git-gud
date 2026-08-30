package com.app.gitgud.mapper;

import com.app.gitgud.model.Question;
import com.app.gitgud.model.dto.QuestionDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionDto toDto(
            Question question
    ) {

        if (question == null) {
            return null;
        }

        return new QuestionDto(
                question.getId(),
                question.getText(),
                question.getOptions(),
                question.getCorrectAnswer(),
                question.getDifficulty(),
                question.getHints(),
                question.getOrder()
        );
    }
}
