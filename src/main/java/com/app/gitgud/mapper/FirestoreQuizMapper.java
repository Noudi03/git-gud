package com.app.gitgud.mapper;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.QuizType;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class FirestoreQuizMapper {

    public Quiz toModel(
            DocumentSnapshot document,
            QuizContext context
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String title =
                document.getString("title");

        String typeValue =
                document.getString("type");

        QuizType type =
                QuizType.fromValue(
                        typeValue
                );

        Long orderValue =
                document.getLong("order");

        int order =
                orderValue != null
                        ? orderValue.intValue()
                        : 0;

        return new Quiz(
                id,
                title,
                type,
                order,
                context
        );
    }
}
