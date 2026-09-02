package com.app.gitgud.mapper;

import com.app.gitgud.model.FillInTheBlankTrial;
import com.app.gitgud.model.TrialDifficulty;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FirestoreFillInTheBlankTrialMapper {

    public FillInTheBlankTrial toModel(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String text =
                document.getString("text");

        String correctAnswer =
                document.getString(
                        "correctAnswer"
                );

        TrialDifficulty difficulty =
                TrialDifficulty.fromValue(
                        document.getString("difficulty")
                );

        List<String> hints =
                convertHints(
                        document.get("hints")
                );

        Long orderValue =
                document.getLong("order");

        int order =
                orderValue != null
                        ? orderValue.intValue()
                        : 0;

        return new FillInTheBlankTrial(
                id,
                text,
                correctAnswer,
                difficulty,
                hints,
                order
        );
    }

    private List<String> convertHints(
            Object value
    ) {

        if (!(value instanceof List<?> rawHints)) {
            return new ArrayList<>();
        }

        List<String> hints =
                new ArrayList<>();

        for (Object hint : rawHints) {

            if (hint != null) {
                hints.add(
                        hint.toString()
                );
            }
        }

        return hints;
    }
}
