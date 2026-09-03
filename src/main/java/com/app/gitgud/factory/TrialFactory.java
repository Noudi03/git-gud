package com.app.gitgud.factory;

import com.app.gitgud.mapper.FirestoreFillInTheBlankTrialMapper;
import com.app.gitgud.mapper.FirestoreMultipleChoiceTrialMapper;
import com.app.gitgud.mapper.FirestoreTrueFalseTrialMapper;
import com.app.gitgud.model.Trial;
import com.app.gitgud.model.TrialType;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class TrialFactory {

    private final FirestoreMultipleChoiceTrialMapper multipleChoiceTrialMapper;
    private final FirestoreFillInTheBlankTrialMapper fillInTheBlankTrialMapper;
    private final FirestoreTrueFalseTrialMapper trueFalseTrialMapper;

    public TrialFactory(
            FirestoreMultipleChoiceTrialMapper multipleChoiceTrialMapper,
            FirestoreFillInTheBlankTrialMapper fillInTheBlankTrialMapper,
            FirestoreTrueFalseTrialMapper trueFalseTrialMapper
    ) {

        this.multipleChoiceTrialMapper =
                multipleChoiceTrialMapper;

        this.fillInTheBlankTrialMapper =
                fillInTheBlankTrialMapper;

        this.trueFalseTrialMapper =
                trueFalseTrialMapper;
    }

    public Trial create(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        TrialType type =
                TrialType.fromValue(
                        document.getString("type")
                );

        return switch (type) {

            case MULTIPLE_CHOICE ->
                    multipleChoiceTrialMapper.toModel(
                            document
                    );

            case FILL_IN_THE_BLANK ->
                    fillInTheBlankTrialMapper.toModel(
                            document
                    );

            case TRUE_FALSE ->
                    trueFalseTrialMapper.toModel(
                            document
                    );
        };
    }
}
