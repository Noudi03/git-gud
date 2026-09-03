package com.app.gitgud.mapper;

import com.app.gitgud.model.TrueFalseTrial;
import com.app.gitgud.model.TrialDifficulty;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirestoreTrueFalseTrialMapper {

    public TrueFalseTrial toModel(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String text =
                document.getString("text");

        Boolean correctAnswer =
                document.getBoolean("correctAnswer");

        if (correctAnswer == null) {
            throw new IllegalArgumentException(
                    "Missing correctAnswer for trial: " + id
            );
        }

        String difficultyValue =
                document.getString("difficulty");

        TrialDifficulty difficulty =
                TrialDifficulty.fromValue(
                        difficultyValue
                );

        List<String> hints =
                (List<String>) document.get("hints");

        Long orderValue =
                document.getLong("order");

        int order =
                orderValue != null
                        ? orderValue.intValue()
                        : 0;

        return new TrueFalseTrial(
                id,
                text,
                correctAnswer,
                difficulty,
                hints,
                order
        );
    }
}
