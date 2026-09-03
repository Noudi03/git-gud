package com.app.gitgud.mapper;

import com.app.gitgud.model.MultipleChoiceTrial;
import com.app.gitgud.model.TrialDifficulty;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FirestoreMultipleChoiceTrialMapper {

    public MultipleChoiceTrial toModel(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String text =
                document.getString("text");

        Map<String, String> options =
                convertOptions(
                        document.get("options")
                );

        String correctAnswer =
                document.getString("correctAnswer");

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

        return new MultipleChoiceTrial(
                id,
                text,
                options,
                correctAnswer,
                difficulty,
                hints,
                order
        );
    }

    private Map<String, String> convertOptions(
            Object value
    ) {

        if (!(value instanceof Map<?, ?> rawOptions)) {
            return new HashMap<>();
        }

        Map<String, String> options =
                new HashMap<>();

        for (
                Map.Entry<?, ?> entry :
                rawOptions.entrySet()
        ) {

            if (
                    entry.getKey() instanceof String key
                            && entry.getValue() != null
            ) {

                options.put(
                        key,
                        entry.getValue().toString()
                );
            }
        }

        return options;
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
