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
                "questionId",
                attempt.getQuestionId()
        );

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

        if (!document.exists()) {
            return null;
        }

        String questionId =
                document.getString("questionId");

        Long timeSpentMillis =
                document.getLong(
                        "timeSpentMillis"
                );

        Long hintsUsed =
                document.getLong(
                        "hintsUsed"
                );

        Long skipCount =
                document.getLong(
                        "skipCount"
                );

        return new QuestionAttempt(
                document.getId(),
                questionId,
                Boolean.TRUE.equals(
                        document.getBoolean("correct")
                ),
                timeSpentMillis != null
                        ? timeSpentMillis
                        : 0,
                hintsUsed != null
                        ? hintsUsed.intValue()
                        : 0,
                skipCount != null
                        ? skipCount.intValue()
                        : 0
        );
    }
}
