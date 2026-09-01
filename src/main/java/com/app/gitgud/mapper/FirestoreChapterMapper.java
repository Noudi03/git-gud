package com.app.gitgud.mapper;

import com.app.gitgud.model.Chapter;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class FirestoreChapterMapper {

    public Chapter toChapter(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String title =
                document.getString("title");

        Long orderValue =
                document.getLong("order");

        int order =
                orderValue != null
                        ? orderValue.intValue()
                        : 0;

        return new Chapter(
                id,
                title,
                order
        );
    }
}
