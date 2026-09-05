package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreQuizAttemptMapper;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
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

    public void updateStatus(
            String userId,
            String attemptId,
            String status
    ) throws Exception {

        getAttemptReference(
                userId,
                attemptId
        )
                .update(
                        "status",
                        status
                )
                .get();
    }

    public boolean markOutcomeProcessed(
            String userId,
            String attemptId
    ) throws Exception {

        DocumentReference reference =
                getAttemptReference(
                        userId,
                        attemptId
                );

        return db.runTransaction(
                transaction -> {

                    var snapshot =
                            transaction.get(
                                    reference
                            ).get();

                    if (!snapshot.exists()) {
                        throw new IllegalArgumentException(
                                "Quiz attempt not found"
                        );
                    }

                    Boolean processed =
                            snapshot.getBoolean(
                                    "outcomeProcessed"
                            );

                    if (
                            processed != null
                                    && processed
                    ) {
                        return false;
                    }

                    String status =
                            snapshot.getString(
                                    "status"
                            );

                    if (
                            !QuizAttemptStatus.IN_PROGRESS
                                    .getValue()
                                    .equals(status)
                    ) {
                        throw new IllegalStateException(
                                "Quiz attempt is not in progress"
                        );
                    }

                    transaction.update(
                            reference,
                            "outcomeProcessed",
                            true
                    );

                    return true;
                }
        ).get();
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
