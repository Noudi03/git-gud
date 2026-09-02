package com.app.gitgud.mapper;

import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
                )
        );
    }
}
