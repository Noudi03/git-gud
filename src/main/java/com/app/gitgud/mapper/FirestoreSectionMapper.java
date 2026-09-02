package com.app.gitgud.mapper;

import com.app.gitgud.model.Section;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class FirestoreSectionMapper {

    public Section toModel(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String title =
                document.getString("title");

        String content =
                document.getString("content");

        Long orderValue =
                document.getLong("order");

        int order =
                orderValue != null
                        ? orderValue.intValue()
                        : 0;

        String advancedContent =
                document.getString("advancedContent");

        return new Section(
                id,
                title,
                content,
                order,
                advancedContent
        );
    }
}
