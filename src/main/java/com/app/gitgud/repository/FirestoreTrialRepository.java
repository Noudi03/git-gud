package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreTrialMapper;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.Trial;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestoreTrialRepository {

    private final Firestore db;
    private final FirestoreTrialMapper mapper;

    public FirestoreTrialRepository(
            Firestore db,
            FirestoreTrialMapper mapper
    ) {

        this.db = db;
        this.mapper = mapper;
    }

    public List<Trial> findTrials(
            Quiz quiz
    ) throws Exception {

        return getTrialsCollection(quiz)
                .orderBy("order")
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(mapper::toModel)
                .filter(trial -> trial != null)
                .toList();
    }

    public Trial findTrial(
            Quiz quiz,
            String trialId
    ) throws Exception {

        return mapper.toModel(
                getTrialsCollection(quiz)
                        .document(trialId)
                        .get()
                        .get()
        );
    }

    private CollectionReference getTrialsCollection(
            Quiz quiz
    ) {

        return db.collection(
                getQuizPath(quiz)
        );
    }

    private String getQuizPath(
            Quiz quiz
    ) {

        return getQuizCollectionPath(
                quiz.getContext()
        ) + "/" + quiz.getId() + "/trials";
    }

    private String getQuizCollectionPath(
            com.app.gitgud.model.QuizContext context
    ) {

        if (
                context instanceof com.app.gitgud.model.SectionQuizContext
                        sectionContext
        ) {

            return "chapters/"
                    + sectionContext.getChapterId()
                    + "/sections/"
                    + sectionContext.getSectionId()
                    + "/quizzes";
        }

        if (
                context instanceof com.app.gitgud.model.ChapterQuizContext
                        chapterContext
        ) {

            return "chapters/"
                    + chapterContext.getChapterId()
                    + "/quizzes";
        }

        if (
                context instanceof com.app.gitgud.model.GlobalQuizContext
        ) {

            return "quizzes";
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }
}
