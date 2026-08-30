package com.app.gitgud.repository;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class QuizFirestoreRepository {

    private final Firestore db;


    public QuizFirestoreRepository(
            Firestore db
    ) {

        this.db = db;
    }


    public CollectionReference getQuizCollection(
            QuizContext context
    ) {

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            return db.collection("chapters")
                    .document(
                            sectionContext.getChapterId()
                    )
                    .collection("sections")
                    .document(
                            sectionContext.getSectionId()
                    )
                    .collection("quizzes");
        }


        if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            return db.collection("chapters")
                    .document(
                            chapterContext.getChapterId()
                    )
                    .collection("quizzes");
        }


        if (
                context instanceof GlobalQuizContext
        ) {

            return db.collection("quizzes");
        }


        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }


    public CollectionReference getQuestionsCollection(
            QuizContext context,
            String quizId
    ) {

        return getQuizCollection(context)
                .document(quizId)
                .collection("questions");
    }
}
