package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreQuizMapper;
import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestoreQuizRepository {

    private final Firestore db;
    private final FirestoreQuizMapper mapper;


    public FirestoreQuizRepository(
            Firestore db,
            FirestoreQuizMapper mapper
    ) {

        this.db = db;
        this.mapper = mapper;
    }


    public List<Quiz> findQuizzes(
            QuizContext context
    ) throws Exception {

        return getQuizCollection(context)
                .orderBy("order")
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(document ->
                        mapper.toQuiz(
                                document,
                                context
                        )
                )
                .filter(quiz -> quiz != null)
                .toList();
    }


    public Quiz findQuiz(
            QuizContext context,
            String quizId
    ) throws Exception {

        return mapper.toQuiz(
                getQuizCollection(context)
                        .document(quizId)
                        .get()
                        .get(),
                context
        );
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
}
