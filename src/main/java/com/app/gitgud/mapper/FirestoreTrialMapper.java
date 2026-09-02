package com.app.gitgud.mapper;

import com.app.gitgud.model.Trial;
import com.app.gitgud.model.TrialType;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class FirestoreTrialMapper {

    private final FirestoreQuestionTrialMapper questionTrialMapper;

    public FirestoreTrialMapper(
            FirestoreQuestionTrialMapper questionTrialMapper
    ) {

        this.questionTrialMapper =
                questionTrialMapper;
    }

    public Trial toModel(
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
                    throw new UnsupportedOperationException(
                            "Fill-in-the-blank trials are not implemented yet"
                    );
        };
    }
}
