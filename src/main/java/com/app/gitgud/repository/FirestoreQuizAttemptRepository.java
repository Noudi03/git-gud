package com.app.gitgud.repository;

import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.QuizAttempt;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FirestoreQuizAttemptRepository {

    private final Firestore db;


    public FirestoreQuizAttemptRepository(
            Firestore db
    ) {

        this.db = db;
    }


    public void createAttempt(
            String userId,
            QuizAttempt attempt
    ) throws Exception {

        List<Map<String, Object>> questionAttempts =
                new ArrayList<>();


        for (
                QuestionAttempt questionAttempt :
                attempt.getQuestionAttempts()
        ) {

            Map<String, Object> data =
                    new HashMap<>();

            data.put(
                    "questionId",
                    questionAttempt.getQuestionId()
            );

            data.put(
                    "correct",
                    questionAttempt.isCorrect()
            );

            data.put(
                    "timeSpentMillis",
                    questionAttempt.getTimeSpentMillis()
            );

            data.put(
                    "hintsUsed",
                    questionAttempt.getHintsUsed()
            );

            data.put(
                    "skipCount",
                    questionAttempt.getSkipCount()
            );

            questionAttempts.add(data);
        }


        Map<String, Object> attemptData =
                new HashMap<>();

        attemptData.put(
                "quizId",
                attempt.getQuizId()
        );

        attemptData.put(
                "status",
                attempt.getStatus().getValue()
        );

        attemptData.put(
                "questionAttempts",
                questionAttempts
        );


        getAttemptCollection(userId)
                .document(attempt.getId())
                .set(attemptData)
                .get();
    }


    private CollectionReference getAttemptCollection(
            String userId
    ) {

        return db.collection("users")
                .document(userId)
                .collection("quizAttempts");
    }
}
