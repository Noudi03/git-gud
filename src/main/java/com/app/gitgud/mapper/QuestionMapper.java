package com.app.gitgud.mapper;

import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.TrueFalseQuestion;
import com.app.gitgud.model.dto.FillInTheBlankQuestionDto;
import com.app.gitgud.model.dto.MultipleChoiceQuestionDto;
import com.app.gitgud.model.dto.QuestionDto;
import com.app.gitgud.model.dto.TrueFalseQuestionDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    private final MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;
    private final FillInTheBlankQuestionMapper fillInTheBlankQuestionMapper;
    private final TrueFalseQuestionMapper trueFalseQuestionMapper;

    public QuestionMapper(
            MultipleChoiceQuestionMapper multipleChoiceQuestionMapper,
            FillInTheBlankQuestionMapper fillInTheBlankQuestionMapper,
            TrueFalseQuestionMapper trueFalseQuestionMapper
    ) {

        this.multipleChoiceQuestionMapper =
                multipleChoiceQuestionMapper;

        this.fillInTheBlankQuestionMapper =
                fillInTheBlankQuestionMapper;

        this.trueFalseQuestionMapper =
                trueFalseQuestionMapper;
    }

    public QuestionDto toDto(
            Question question
    ) {

        if (question == null) {
            return null;
        }

        return switch (question.getType()) {

            case MULTIPLE_CHOICE ->
                    multipleChoiceQuestionMapper.toDto(
                            (MultipleChoiceQuestion) question
                    );

            case FILL_IN_THE_BLANK ->
                    fillInTheBlankQuestionMapper.toDto(
                            (FillInTheBlankQuestion) question
                    );

            case TRUE_FALSE ->
                    trueFalseQuestionMapper.toDto(
                            (TrueFalseQuestion) question
                    );
        };
    }
}
