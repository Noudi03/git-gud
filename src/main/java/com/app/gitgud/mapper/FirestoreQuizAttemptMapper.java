package com.app.gitgud.mapper;

import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FirestoreQuizAttemptMapper {

    public Map<String, Object> toData(
            QuizAttempt attempt
    ) {
        Map<String, Object> data =
                new HashMap<>();

        data.put(
                "quizId",
                attempt.getQuizId()
        );

        data.put(
                "status",
                attempt.getStatus().getValue()
        );

        data.put(
                "questionAttempts",
                attempt.getQuestionAttempts()
                        .stream()
                        .map(this::toData)
                        .toList()
        );

        return data;
    }

    private Map<String, Object> toData(
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

    public QuizAttempt toModel(
            DocumentSnapshot document
    ) {
        return new QuizAttempt(
                document.getId(),
                document.getString("quizId"),
                QuizAttemptStatus.fromValue(
                        document.getString("status")
                ),
                toQuestionAttempts(
                        document.get("questionAttempts")
                )
        );
    }

    private List<QuestionAttempt> toQuestionAttempts(
            Object value
    ) {
        if (!(value instanceof List<?> list)) {
            return List.of();
        }

        List<QuestionAttempt> attempts =
                new ArrayList<>();

        for (Object item : list) {
            if (item instanceof Map<?, ?> map) {
                attempts.add(
                        toQuestionAttempt(map)
                );
            }
        }

        return attempts;
    }

    private QuestionAttempt toQuestionAttempt(
            Map<?, ?> data
    ) {
        return new QuestionAttempt(
                (String) data.get("questionId"),
                Boolean.TRUE.equals(
                        data.get("correct")
                ),
                ((Number) data.get("timeSpentMillis"))
                        .longValue(),
                ((Number) data.get("hintsUsed"))
                        .intValue(),
                ((Number) data.get("skipCount"))
                        .intValue()
        );
    }
}
