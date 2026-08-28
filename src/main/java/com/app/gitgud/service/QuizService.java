package com.app.gitgud.service;

import com.app.gitgud.model.Quiz;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

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

        return db.collection("chapters")
                .document(chapterId)
                .collection("sections")
                .document(sectionId)
                .collection("quizzes")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Quiz.class);
    }

    public Quiz getSectionQuiz(
            String chapterId,
            String sectionId,
            String quizId
    ) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .collection("sections")
                .document(sectionId)
                .collection("quizzes")
                .document(quizId)
                .get()
                .get()
                .toObject(Quiz.class);
    }

    public List<Quiz> getChapterQuizzes(
            String chapterId
    ) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .collection("quizzes")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Quiz.class);
    }

    public Quiz getChapterQuiz(
            String chapterId,
            String quizId
    ) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .collection("quizzes")
                .document(quizId)
                .get()
                .get()
                .toObject(Quiz.class);
    }

    public List<Quiz> getGlobalQuizzes() throws Exception {

        return db.collection("quizzes")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Quiz.class);
    }

    public Quiz getGlobalQuiz(
            String quizId
    ) throws Exception {

        return db.collection("quizzes")
                .document(quizId)
                .get()
                .get()
                .toObject(Quiz.class);
    }
}
