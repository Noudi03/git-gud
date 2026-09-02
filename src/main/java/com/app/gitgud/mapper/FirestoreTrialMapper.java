package com.app.gitgud.mapper;

import com.app.gitgud.factory.TrialFactory;
import com.app.gitgud.model.Trial;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class FirestoreTrialMapper {

    private final TrialFactory trialFactory;

    public FirestoreTrialMapper(
            TrialFactory trialFactory
    ) {

        this.trialFactory =
                trialFactory;
    }

    public Trial toModel(
            DocumentSnapshot document
    ) {

        return trialFactory.create(
                document
        );
    }
}
