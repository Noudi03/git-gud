package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreTrialAttemptMapper;
import com.app.gitgud.model.TrialAttempt;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FirestoreTrialAttemptRepository {

    private final Firestore db;
    private final FirestoreTrialAttemptMapper mapper;

    public FirestoreTrialAttemptRepository(
            Firestore db,
            FirestoreTrialAttemptMapper mapper
    ) {

        this.db = db;
        this.mapper = mapper;
    }

    public void createTrialAttempt(
            String userId,
            String attemptId,
            TrialAttempt trialAttempt
    ) throws Exception {

        getTrialAttemptCollection(
                userId,
                attemptId
        )
                .document(trialAttempt.getTrialId())
                .set(mapper.toData(trialAttempt))
                .get();
    }

    public Optional<TrialAttempt> findTrialAttempt(
            String userId,
            String attemptId,
            String trialId
    ) throws Exception {

        var document =
                getTrialAttemptCollection(
                        userId,
                        attemptId
                )
                        .document(trialId)
                        .get()
                        .get();

        if (!document.exists()) {
            return Optional.empty();
        }

        return Optional.of(
                mapper.toModel(document)
        );
    }

    public List<TrialAttempt> findTrialAttempts(
            String userId,
            String attemptId
    ) throws Exception {

        return getTrialAttemptCollection(
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

    private CollectionReference getTrialAttemptCollection(
            String userId,
            String attemptId
    ) {

        return db.collection("users")
                .document(userId)
                .collection("quizAttempts")
                .document(attemptId)
                .collection("trialAttempts");
    }
}
