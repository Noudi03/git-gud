package com.app.gitgud.factory;

import com.app.gitgud.mapper.FirestoreFillInTheBlankTrialMapper;
import com.app.gitgud.mapper.FirestoreQuestionTrialMapper;
import com.app.gitgud.model.Trial;
import com.app.gitgud.model.TrialType;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class TrialFactory {

    private final FirestoreQuestionTrialMapper questionTrialMapper;
    private final FirestoreFillInTheBlankTrialMapper fillInTheBlankTrialMapper;

    public TrialFactory(
            FirestoreQuestionTrialMapper questionTrialMapper,
            FirestoreFillInTheBlankTrialMapper fillInTheBlankTrialMapper
    ) {

        this.questionTrialMapper =
                questionTrialMapper;

        this.fillInTheBlankTrialMapper =
                fillInTheBlankTrialMapper;
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

            case QUESTION ->
                    questionTrialMapper.toModel(
                            document
                    );

            case FILL_IN_THE_BLANK ->
                    fillInTheBlankTrialMapper.toModel(
                            document
                    );
        };
    }
}
