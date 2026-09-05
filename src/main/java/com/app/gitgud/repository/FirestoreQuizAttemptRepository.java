package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreQuizAttemptMapper;
import com.app.gitgud.model.QuizAttempt;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestoreQuizAttemptRepository {

    private final Firestore db;
    private final FirestoreQuizAttemptMapper mapper;

    public FirestoreQuizAttemptRepository(
            Firestore db,
            FirestoreQuizAttemptMapper mapper
    ) {
        this.db = db;
        this.mapper = mapper;
    }

    public void createAttempt(
            String userId,
            QuizAttempt attempt
    ) throws Exception {

        getAttemptReference(
                userId,
                attempt.getId()
        )
                .set(
                        mapper.toData(attempt)
                )
                .get();
    }

    public List<QuizAttempt> findAttempts(
            String userId
    ) throws Exception {

        return db.collection("users")
                .document(userId)
                .collection("quizAttempts")
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(mapper::toModel)
                .filter(attempt -> attempt != null)
                .toList();
    }

    public QuizAttempt findAttempt(
            String userId,
            String attemptId
    ) throws Exception {

        return mapper.toModel(
                getAttemptReference(
                        userId,
                        attemptId
                )
                        .get()
                        .get()
        );
    }

    private DocumentReference getAttemptReference(
            String userId,
            String attemptId
    ) {

        return db.collection("users")
                .document(userId)
                .collection("quizAttempts")
                .document(attemptId);
    }
}
