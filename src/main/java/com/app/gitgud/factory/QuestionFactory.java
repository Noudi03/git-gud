package com.app.gitgud.factory;

import com.app.gitgud.mapper.FirestoreFillInTheBlankQuestionMapper;
import com.app.gitgud.mapper.FirestoreMultipleChoiceQuestionMapper;
import com.app.gitgud.mapper.FirestoreTrueFalseQuestionMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.QuestionType;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class QuestionFactory {

    private final FirestoreMultipleChoiceQuestionMapper multipleChoiceQuestionMapper;
    private final FirestoreFillInTheBlankQuestionMapper fillInTheBlankQuestionMapper;
    private final FirestoreTrueFalseQuestionMapper trueFalseQuestionMapper;

    public QuestionFactory(
            FirestoreMultipleChoiceQuestionMapper multipleChoiceQuestionMapper,
            FirestoreFillInTheBlankQuestionMapper fillInTheBlankQuestionMapper,
            FirestoreTrueFalseQuestionMapper trueFalseQuestionMapper
    ) {

        this.multipleChoiceQuestionMapper =
                multipleChoiceQuestionMapper;

        this.fillInTheBlankQuestionMapper =
                fillInTheBlankQuestionMapper;

        this.trueFalseQuestionMapper =
                trueFalseQuestionMapper;
    }

    public Question create(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        QuestionType type =
                QuestionType.fromValue(
                        document.getString("type")
                );

        return switch (type) {

            case MULTIPLE_CHOICE ->
                    multipleChoiceQuestionMapper.toModel(
                            document
                    );

            case FILL_IN_THE_BLANK ->
                    fillInTheBlankQuestionMapper.toModel(
                            document
                    );

            case TRUE_FALSE ->
                    trueFalseQuestionMapper.toModel(
                            document
                    );
        };
    }
}
