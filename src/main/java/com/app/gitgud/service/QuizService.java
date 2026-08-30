package com.app.gitgud.service;

import com.app.gitgud.mapper.QuizFirestoreMapper;
import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.repository.QuizFirestoreRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizFirestoreRepository quizFirestoreRepository;
    private final QuizFirestoreMapper quizFirestoreMapper;


    public QuizService(
            QuizFirestoreRepository quizFirestoreRepository,
            QuizFirestoreMapper quizFirestoreMapper
    ) {

        this.quizFirestoreRepository =
                quizFirestoreRepository;

        this.quizFirestoreMapper =
                quizFirestoreMapper;
    }


    public List<Quiz> getSectionQuizzes(
            String chapterId,
            String sectionId
    ) throws Exception {

        QuizContext context =
                new SectionQuizContext(
                        chapterId,
                        sectionId
                );

        QuerySnapshot snapshot =
                quizFirestoreRepository
                        .getQuizCollection(context)
                        .orderBy("order")
                        .get()
                        .get();

        return convertQuizzes(
                snapshot,
                context
        );
    }


    public Quiz getSectionQuiz(
            String chapterId,
            String sectionId,
            String quizId
    ) throws Exception {

        QuizContext context =
                new SectionQuizContext(
                        chapterId,
                        sectionId
                );

        DocumentSnapshot document =
                quizFirestoreRepository
                        .getQuizCollection(context)
                        .document(quizId)
                        .get()
                        .get();

        return quizFirestoreMapper.toQuiz(
                document,
                context
        );
    }


    public List<Quiz> getChapterQuizzes(
            String chapterId
    ) throws Exception {

        QuizContext context =
                new ChapterQuizContext(
                        chapterId
                );

        QuerySnapshot snapshot =
                quizFirestoreRepository
                        .getQuizCollection(context)
                        .orderBy("order")
                        .get()
                        .get();

        return convertQuizzes(
                snapshot,
                context
        );
    }


    public Quiz getChapterQuiz(
            String chapterId,
            String quizId
    ) throws Exception {

        QuizContext context =
                new ChapterQuizContext(
                        chapterId
                );

        DocumentSnapshot document =
                quizFirestoreRepository
                        .getQuizCollection(context)
                        .document(quizId)
                        .get()
                        .get();

        return quizFirestoreMapper.toQuiz(
                document,
                context
        );
    }


    public List<Quiz> getGlobalQuizzes()
            throws Exception {

        QuizContext context =
                new GlobalQuizContext();

        QuerySnapshot snapshot =
                quizFirestoreRepository
                        .getQuizCollection(context)
                        .orderBy("order")
                        .get()
                        .get();

        return convertQuizzes(
                snapshot,
                context
        );
    }


    public Quiz getGlobalQuiz(
            String quizId
    ) throws Exception {

        QuizContext context =
                new GlobalQuizContext();

        DocumentSnapshot document =
                quizFirestoreRepository
                        .getQuizCollection(context)
                        .document(quizId)
                        .get()
                        .get();

        return quizFirestoreMapper.toQuiz(
                document,
                context
        );
    }


    private List<Quiz> convertQuizzes(
            QuerySnapshot snapshot,
            QuizContext context
    ) {

        List<Quiz> quizzes =
                new ArrayList<>();

        for (
                DocumentSnapshot document :
                snapshot.getDocuments()
        ) {

            Quiz quiz =
                    quizFirestoreMapper.toQuiz(
                            document,
                            context
                    );

            if (quiz != null) {
                quizzes.add(quiz);
            }
        }

        return quizzes;
    }
}
