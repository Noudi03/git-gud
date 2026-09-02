package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreQuestionMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestoreQuestionRepository {

    private final Firestore db;
    private final FirestoreQuestionMapper mapper;


    public FirestoreQuestionRepository(
            Firestore db,
            FirestoreQuestionMapper mapper
    ) {

        this.db = db;
        this.mapper = mapper;
    }


    public List<Question> findQuestions(
            Quiz quiz
    ) throws Exception {

        return getQuestionsCollection(quiz)
                .orderBy("order")
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(mapper::toQuestion)
                .filter(question -> question != null)
                .toList();
    }


    public Question findQuestion(
            Quiz quiz,
            String questionId
    ) throws Exception {

        return mapper.toQuestion(
                getQuestionsCollection(quiz)
                        .document(questionId)
                        .get()
                        .get()
        );
    }


    private CollectionReference getQuestionsCollection(
            Quiz quiz
    ) {

        return db.collection(
                getQuizPath(
                        quiz
                )
        );
    }


    private String getQuizPath(
            Quiz quiz
    ) {

        return getQuizCollectionPath(
                quiz.getContext()
        ) + "/" + quiz.getId() + "/questions";
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
