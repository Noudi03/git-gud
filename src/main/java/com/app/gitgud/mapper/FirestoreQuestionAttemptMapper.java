package com.app.gitgud.mapper;

import com.app.gitgud.model.QuestionAttempt;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FirestoreQuestionAttemptMapper {

    public Map<String, Object> toData(
            QuestionAttempt attempt
    ) {
        Map<String, Object> data =
                new HashMap<>();

        data.put(
                "correct",
                attempt.isCorrect()
        );

        data.put(
                "timeSpentMillis",
                attempt.getTimeSpentMillis()
        );

        data.put(
                "hintsUsed",
                attempt.getHintsUsed()
        );

        data.put(
                "skipCount",
                attempt.getSkipCount()
        );

        return data;
    }

    public QuestionAttempt toModel(
            DocumentSnapshot document
    ) {
        return new QuestionAttempt(
                document.getId(),
                Boolean.TRUE.equals(
                        document.getBoolean("correct")
                ),
                document.getLong("timeSpentMillis") != null
                        ? document.getLong("timeSpentMillis")
                        : 0,
                document.getLong("hintsUsed") != null
                        ? document.getLong("hintsUsed").intValue()
                        : 0,
                document.getLong("skipCount") != null
                        ? document.getLong("skipCount").intValue()
                        : 0
        );
    }
}
