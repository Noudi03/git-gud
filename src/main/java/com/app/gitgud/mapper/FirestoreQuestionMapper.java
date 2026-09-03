package com.app.gitgud.mapper;

import com.app.gitgud.factory.QuestionFactory;
import com.app.gitgud.model.Question;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class FirestoreQuestionMapper {

    private final QuestionFactory questionFactory;

    public FirestoreQuestionMapper(
            QuestionFactory questionFactory
    ) {

        this.questionFactory =
                questionFactory;
    }

    public Question toModel(
            DocumentSnapshot document
    ) {

        return questionFactory.create(
                document
        );
    }
}
