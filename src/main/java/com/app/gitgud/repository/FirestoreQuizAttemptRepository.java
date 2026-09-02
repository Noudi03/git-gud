package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreQuizAttemptMapper;
import com.app.gitgud.model.QuizAttempt;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

        getAttemptCollection(userId)
                .document(attempt.getId())
                .set(mapper.toData(attempt))
                .get();
    }

    public Optional<QuizAttempt> findAttempt(
            String userId,
            String attemptId
    ) throws Exception {

        var document =
                getAttemptCollection(userId)
                        .document(attemptId)
                        .get()
                        .get();

        if (!document.exists()) {
            return Optional.empty();
        }

        return Optional.of(
                mapper.toModel(document)
        );
    }

    public List<QuizAttempt> findAttempts(
            String userId
    ) throws Exception {

        return getAttemptCollection(userId)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    private CollectionReference getAttemptCollection(
            String userId
    ) {
        return db.collection("users")
                .document(userId)
                .collection("quizAttempts");
    }
}
