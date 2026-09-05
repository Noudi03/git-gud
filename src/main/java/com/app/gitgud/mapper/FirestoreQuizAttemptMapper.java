package com.app.gitgud.mapper;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FirestoreQuizAttemptMapper {

    public Map<String, Object> toData(
            QuizAttempt attempt
    ) {

        Map<String, Object> data =
                new HashMap<>();

        data.put(
                "quizId",
                attempt.getQuizId()
        );

        data.put(
                "status",
                attempt.getStatus().getValue()
        );

        data.put(
                "outcomeProcessed",
                attempt.isOutcomeProcessed()
        );

        QuizContext context =
                attempt.getContext();

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            data.put(
                    "contextType",
                    "section"
            );

            data.put(
                    "chapterId",
                    sectionContext.getChapterId()
            );

            data.put(
                    "sectionId",
                    sectionContext.getSectionId()
            );

        } else if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            data.put(
                    "contextType",
                    "chapter"
            );

            data.put(
                    "chapterId",
                    chapterContext.getChapterId()
            );

        } else if (
                context instanceof GlobalQuizContext
        ) {

            data.put(
                    "contextType",
                    "global"
            );

        } else {

            throw new IllegalArgumentException(
                    "Unsupported quiz context: "
                            + context.getClass().getName()
            );
        }

        return data;
    }

    public QuizAttempt toModel(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        String contextType =
                document.getString("contextType");

        QuizContext context;

        switch (contextType) {

            case "section":
                context =
                        new SectionQuizContext(
                                document.getString("chapterId"),
                                document.getString("sectionId")
                        );
                break;

            case "chapter":
                context =
                        new ChapterQuizContext(
                                document.getString("chapterId")
                        );
                break;

            case "global":
                context =
                        new GlobalQuizContext();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown quiz context type: "
                                + contextType
                );
        }

        Boolean outcomeProcessed =
                document.getBoolean(
                        "outcomeProcessed"
                );

        return new QuizAttempt(
                document.getId(),
                document.getString("quizId"),
                QuizAttemptStatus.fromValue(
                        document.getString("status")
                ),
                context,
                outcomeProcessed != null
                        && outcomeProcessed
        );
    }
}
