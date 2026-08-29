package com.app.gitgud.service;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    private final Firestore db;

    public QuestionService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<Question> getQuizQuestions(
            Quiz quiz
    ) throws Exception {

        QuerySnapshot snapshot =
                getQuestionsCollection(quiz)
                        .orderBy("order")
                        .get()
                        .get();

        return convertQuestions(snapshot);
    }

    public Question getQuizQuestion(
            Quiz quiz,
            String questionId
    ) throws Exception {

        DocumentSnapshot document =
                getQuestionsCollection(quiz)
                        .document(questionId)
                        .get()
                        .get();

        return convertQuestion(document);
    }

    private List<Question> convertQuestions(
            QuerySnapshot snapshot
    ) {

        List<Question> questions =
                new ArrayList<>();

        for (DocumentSnapshot document :
                snapshot.getDocuments()) {

            Question question =
                    convertQuestion(document);

            if (question != null) {
                questions.add(question);
            }
        }

        return questions;
    }

    private Question convertQuestion(
            DocumentSnapshot document
    ) {

        if (!document.exists()) {
            return null;
        }

        String id =
                document.getId();

        String text =
                document.getString("text");

        Map<String, String> options =
                convertOptions(
                        document.get("options")
                );

        String correctAnswer =
                document.getString("correctAnswer");

        String difficultyValue =
                document.getString("difficulty");

        QuestionDifficulty difficulty =
                QuestionDifficulty.fromValue(
                        difficultyValue
                );

        List<String> hints =
                convertHints(
                        document.get("hints")
                );

        Long orderValue =
                document.getLong("order");

        int order =
                orderValue != null
                        ? orderValue.intValue()
                        : 0;

        return new Question(
                id,
                text,
                options,
                correctAnswer,
                difficulty,
                hints,
                order
        );
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> convertOptions(
            Object value
    ) {

        if (value == null) {
            return new HashMap<>();
        }

        Map<String, Object> rawOptions =
                (Map<String, Object>) value;

        Map<String, String> options =
                new HashMap<>();

        for (Map.Entry<String, Object> entry :
                rawOptions.entrySet()) {

            if (entry.getValue() != null) {

                options.put(
                        entry.getKey(),
                        entry.getValue().toString()
                );
            }
        }

        return options;
    }

    @SuppressWarnings("unchecked")
    private List<String> convertHints(
            Object value
    ) {

        if (value == null) {
            return new ArrayList<>();
        }

        List<Object> rawHints =
                (List<Object>) value;

        List<String> hints =
                new ArrayList<>();

        for (Object hint : rawHints) {

            if (hint != null) {
                hints.add(hint.toString());
            }
        }

        return hints;
    }

    private CollectionReference getQuestionsCollection(
            Quiz quiz
    ) {

        QuizContext context =
                quiz.getContext();

        if (context instanceof SectionQuizContext sectionContext) {

            return db.collection("chapters")
                    .document(sectionContext.getChapterId())
                    .collection("sections")
                    .document(sectionContext.getSectionId())
                    .collection("quizzes")
                    .document(quiz.getId())
                    .collection("questions");
        }

        if (context instanceof ChapterQuizContext chapterContext) {

            return db.collection("chapters")
                    .document(chapterContext.getChapterId())
                    .collection("quizzes")
                    .document(quiz.getId())
                    .collection("questions");
        }

        // Global quiz
        return db.collection("quizzes")
                .document(quiz.getId())
                .collection("questions");
    }
}
