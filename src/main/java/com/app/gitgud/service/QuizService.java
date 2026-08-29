package com.app.gitgud.service;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.QuizType;
import com.app.gitgud.model.SectionQuizContext;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final Firestore db;

    public QuizService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<Quiz> getSectionQuizzes(
            String chapterId,
            String sectionId
    ) throws Exception {

        QuerySnapshot snapshot =
                db.collection("chapters")
                        .document(chapterId)
                        .collection("sections")
                        .document(sectionId)
                        .collection("quizzes")
                        .orderBy("order")
                        .get()
                        .get();

        QuizContext context =
                new SectionQuizContext(
                        chapterId,
                        sectionId
                );

        return convertQuizzes(snapshot, context);
    }

    public Quiz getSectionQuiz(
            String chapterId,
            String sectionId,
            String quizId
    ) throws Exception {

        DocumentSnapshot document =
                db.collection("chapters")
                        .document(chapterId)
                        .collection("sections")
                        .document(sectionId)
                        .collection("quizzes")
                        .document(quizId)
                        .get()
                        .get();

        QuizContext context =
                new SectionQuizContext(
                        chapterId,
                        sectionId
                );

        return convertQuiz(document, context);
    }

    public List<Quiz> getChapterQuizzes(
            String chapterId
    ) throws Exception {

        QuerySnapshot snapshot =
                db.collection("chapters")
                        .document(chapterId)
                        .collection("quizzes")
                        .orderBy("order")
                        .get()
                        .get();

        QuizContext context =
                new ChapterQuizContext(chapterId);

        return convertQuizzes(snapshot, context);
    }

    public Quiz getChapterQuiz(
            String chapterId,
            String quizId
    ) throws Exception {

        DocumentSnapshot document =
                db.collection("chapters")
                        .document(chapterId)
                        .collection("quizzes")
                        .document(quizId)
                        .get()
                        .get();

        QuizContext context =
                new ChapterQuizContext(chapterId);

        return convertQuiz(document, context);
    }

    public List<Quiz> getGlobalQuizzes() throws Exception {

        QuerySnapshot snapshot =
                db.collection("quizzes")
                        .orderBy("order")
                        .get()
                        .get();

        return convertQuizzes(snapshot, null);
    }

    public Quiz getGlobalQuiz(
            String quizId
    ) throws Exception {

        DocumentSnapshot document =
                db.collection("quizzes")
                        .document(quizId)
                        .get()
                        .get();

        return convertQuiz(document, null);
    }

    private List<Quiz> convertQuizzes(
            QuerySnapshot snapshot,
            QuizContext context
    ) {

        List<Quiz> quizzes = new ArrayList<>();

        for (DocumentSnapshot document : snapshot.getDocuments()) {

            Quiz quiz =
                    convertQuiz(document, context);

            if (quiz != null) {
                quizzes.add(quiz);
            }
        }

        return quizzes;
    }

    private Quiz convertQuiz(
            DocumentSnapshot document,
            QuizContext context
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String title =
                document.getString("title");

        String typeValue =
                document.getString("type");

        QuizType type =
                QuizType.fromValue(typeValue);

        Long orderValue =
                document.getLong("order");

        int order =
                orderValue != null
                        ? orderValue.intValue()
                        : 0;

        return new Quiz(
                id,
                title,
                type,
                order,
                context
        );
    }
}
