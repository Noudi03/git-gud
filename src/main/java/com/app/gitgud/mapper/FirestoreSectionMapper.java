package com.app.gitgud.mapper;

import com.app.gitgud.model.Section;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

@Component
public class FirestoreSectionMapper {

    public Section toSection(
            DocumentSnapshot document,
            String chapterId
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

        String prerequisiteId =
                document.getString("prerequisiteId");

        Boolean lockedValue =
                document.getBoolean("locked");

        boolean locked =
                lockedValue != null
                        && lockedValue;

        String advancedContent =
                document.getString("advancedContent");

        return new Section(
                id,
                chapterId,
                title,
                content,
                order,
                prerequisiteId,
                locked,
                advancedContent
        );
    }
}
