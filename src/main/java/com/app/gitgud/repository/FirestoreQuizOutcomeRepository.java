package com.app.gitgud.repository;

import com.app.gitgud.config.GitGudProperties;
import com.app.gitgud.model.QuizAttemptStatus;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class FirestoreQuizOutcomeRepository {

    private final Firestore db;
    private final GitGudProperties properties;

    public FirestoreQuizOutcomeRepository(
            Firestore db,
            GitGudProperties properties
    ) {

        this.db = db;
        this.properties = properties;
    }

    public boolean finalizeAttempt(
            String userId,
            String attemptId,
            QuizAttemptStatus finalStatus,
            Integer ratingChange,
            Integer absoluteRating
    ) throws Exception {

        if (
                finalStatus != QuizAttemptStatus.COMPLETED
                        && finalStatus != QuizAttemptStatus.CANCELLED
        ) {

            throw new IllegalArgumentException(
                    "Invalid final quiz attempt status"
            );
        }

        if (
                ratingChange != null &&
                        absoluteRating != null
        ) {

            throw new IllegalArgumentException(
                    "Cannot apply both rating change and absolute rating"
            );
        }

        DocumentReference attemptReference =
                getAttemptReference(
                        userId,
                        attemptId
                );

        DocumentReference userReference =
                getUserReference(
                        userId
                );

        return db.runTransaction(
                transaction -> {

                    DocumentSnapshot attempt =
                            transaction.get(
                                    attemptReference
                            ).get();

                    if (!attempt.exists()) {

                        throw new IllegalArgumentException(
                                "Quiz attempt not found"
                        );
                    }

                    Boolean outcomeProcessed =
                            attempt.getBoolean(
                                    "outcomeProcessed"
                            );

                    if (
                            Boolean.TRUE.equals(
                                    outcomeProcessed
                            )
                    ) {

                        return false;
                    }

                    String currentStatus =
                            attempt.getString(
                                    "status"
                            );

                    if (
                            !QuizAttemptStatus.IN_PROGRESS
                                    .getValue()
                                    .equals(
                                            currentStatus
                                    )
                    ) {

                        throw new IllegalStateException(
                                "Quiz attempt is not in progress"
                        );
                    }

                    if (
                            ratingChange != null ||
                                    absoluteRating != null
                    ) {

                        DocumentSnapshot user =
                                transaction.get(
                                        userReference
                                ).get();

                        if (!user.exists()) {

                            throw new IllegalArgumentException(
                                    "User not found"
                            );
                        }

                        Long storedRating =
                                user.getLong("rating");

                        int currentRating =
                                storedRating == null
                                        ? properties
                                        .getRating()
                                        .getDefaultRating()
                                        : clampRating(
                                        storedRating
                                                .intValue()
                                );

                        int newRating;

                        if (absoluteRating != null) {

                            newRating =
                                    clampRating(
                                            absoluteRating
                                    );

                        } else {

                            long candidate =
                                    (long) currentRating
                                            + ratingChange;

                            newRating =
                                    clampRating(
                                            candidate
                                    );
                        }

                        transaction.update(
                                userReference,
                                "rating",
                                newRating
                        );
                    }

                    transaction.update(
                            attemptReference,
                            "status",
                            finalStatus.getValue(),
                            "outcomeProcessed",
                            true
                    );

                    return true;
                }
        ).get();
    }

    private int clampRating(
            long rating
    ) {

        int minimum =
                properties.getRating().getMin();

        if (rating <= minimum) {
            return minimum;
        }

        if (rating >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return (int) rating;
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

    private DocumentReference getUserReference(
            String userId
    ) {

        return db.collection("users")
                .document(userId);
    }
}
