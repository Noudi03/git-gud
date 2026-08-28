package com.app.gitgud.service;

import com.app.gitgud.model.Question;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final Firestore db;

    public QuestionService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<Question> getSectionQuizQuestions(
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
                .collection("questions")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Question.class);
    }

    public List<Question> getChapterQuizQuestions(
            String chapterId,
            String quizId
    ) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .collection("quizzes")
                .document(quizId)
                .collection("questions")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Question.class);
    }

    public List<Question> getReviewQuizQuestions(
            String quizId
    ) throws Exception {

        return db.collection("quizzes")
                .document(quizId)
                .collection("questions")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Question.class);
    }
}
