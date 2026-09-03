package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreQuestionAttemptMapper;
import com.app.gitgud.model.QuestionAttempt;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FirestoreQuestionAttemptRepository {

    private final Firestore db;
    private final FirestoreQuestionAttemptMapper mapper;

    public FirestoreQuestionAttemptRepository(
            Firestore db,
            FirestoreQuestionAttemptMapper mapper
    ) {

        this.db = db;
        this.mapper = mapper;
    }

    public void createQuestionAttempt(
            String userId,
            String attemptId,
            QuestionAttempt questionAttempt
    ) throws Exception {

        getQuestionAttemptCollection(
                userId,
                attemptId
        )
                .document(questionAttempt.getQuestionId())
                .set(mapper.toData(questionAttempt))
                .get();
    }

    public Optional<QuestionAttempt> findQuestionAttempt(
            String userId,
            String attemptId,
            String questionId
    ) throws Exception {

        var document =
                getQuestionAttemptCollection(
                        userId,
                        attemptId
                )
                        .document(questionId)
                        .get()
                        .get();

        if (!document.exists()) {
            return Optional.empty();
        }

        return Optional.of(
                mapper.toModel(document)
        );
    }

    public List<QuestionAttempt> findQuestionAttempts(
            String userId,
            String attemptId
    ) throws Exception {

        return getQuestionAttemptCollection(
                userId,
                attemptId
        )
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(mapper::toModel)
                .filter(attempt -> attempt != null)
                .toList();
    }

    private CollectionReference getQuestionAttemptCollection(
            String userId,
            String attemptId
    ) {

        return db.collection("users")
                .document(userId)
                .collection("quizAttempts")
                .document(attemptId)
                .collection("questionAttempts");
    }
}
