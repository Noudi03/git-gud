package com.app.gitgud.service;

import com.app.gitgud.mapper.FirestoreQuizMapper;
import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.repository.FirestoreQuizRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final FirestoreQuizRepository firestoreQuizRepository;
    private final FirestoreQuizMapper firestoreQuizMapper;


    public QuizService(
            FirestoreQuizRepository firestoreQuizRepository,
            FirestoreQuizMapper firestoreQuizMapper
    ) {

        this.firestoreQuizRepository =
                firestoreQuizRepository;

        this.firestoreQuizMapper =
                firestoreQuizMapper;
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
                firestoreQuizRepository
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
                firestoreQuizRepository
                        .getQuizCollection(context)
                        .document(quizId)
                        .get()
                        .get();

        return firestoreQuizMapper.toQuiz(
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
                firestoreQuizRepository
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
                firestoreQuizRepository
                        .getQuizCollection(context)
                        .document(quizId)
                        .get()
                        .get();

        return firestoreQuizMapper.toQuiz(
                document,
                context
        );
    }


    public List<Quiz> getGlobalQuizzes()
            throws Exception {

        QuizContext context =
                new GlobalQuizContext();

        QuerySnapshot snapshot =
                firestoreQuizRepository
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
                firestoreQuizRepository
                        .getQuizCollection(context)
                        .document(quizId)
                        .get()
                        .get();

        return firestoreQuizMapper.toQuiz(
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
                    firestoreQuizMapper.toQuiz(
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
