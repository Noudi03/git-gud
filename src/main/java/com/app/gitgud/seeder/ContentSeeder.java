package com.app.gitgud.seeder;

import com.app.gitgud.model.*;
import com.app.gitgud.seeder.data.chapter1.Chapter1Data;
import com.app.gitgud.seeder.data.chapter2.Chapter2Data;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ContentSeeder implements CommandLineRunner {

    @Value("${app.seeder.enabled:false}")
    private boolean enabled;

    @Value("${app.seeder.reset:false}")
    private boolean reset;

    @Override
    public void run(String... args) throws Exception {
        if (!enabled) {
            System.out.println("Seeder disabled.");
            return;
        }

        Firestore db = FirestoreClient.getFirestore();

        if (reset) {
            System.out.println("Resetting database...");
            deleteCollection(db.collection("chapters"));
            deleteCollection(db.collection("quizzes"));
            System.out.println("Database cleared.");
        } else if (!db.collection("chapters").get().get().isEmpty()) {
            System.out.println("DB already seeded, skipping.");
            return;
        }

        List<ChapterData> chapters = Arrays.asList(
                new Chapter1Data(),
                new Chapter2Data()
                // new Chapter3Data()
        );

        for (ChapterData data : chapters) {
            seedChapter(db, data);
            System.out.println("Seeded chapter: " + data.getChapter().getTitle());
        }
        seedReviewQuiz(db, chapters);
        System.out.println("Seeding completed.");
    }

    // quizes are stored as subcollections within their parent section or parent chapter (except of global quizes)
    // questions are stored as a subcolletion (under their quiz)
    private void seedChapter(Firestore db, ChapterData data) throws Exception {

        DocumentReference chapterRef = db.collection("chapters").document(data.getChapter().getId());
        chapterRef.set(data.getChapter()).get();

        Map<String, List<QuestionTrial>> allQuestions = data.getQuestions();

        for (Section section : data.getSections()) {

            DocumentReference sectionRef = chapterRef.collection("sections").document(section.getId());
            sectionRef.set(section).get();

            List<QuestionTrial> questionTrials = allQuestions.getOrDefault(section.getId(), List.of());

            // section level practice quiz
            if (!questionTrials.isEmpty()) {
                String quizId = "quiz-practice";

                Quiz practiceQuiz = new Quiz();
                practiceQuiz.setId(quizId);
                practiceQuiz.setTitle("Practice Quiz");
                practiceQuiz.setType(QuizType.PRACTICE);
                practiceQuiz.setOrder(1);

                sectionRef.collection("quizzes").document(quizId).set(practiceQuiz).get();

                for (QuestionTrial q : questionTrials) {
                    sectionRef.collection("quizzes")
                            .document(quizId)
                            .collection("questionTrials")
                            .document(q.getId())
                            .set(q)
                            .get();
                }
            }
        }
        // todo: make it skill based
        // Chapter-level final quiz: one question per section
        List<QuestionTrial> finalQuestionTrials = data.getSections().stream()
                .map(section -> {
                    List<QuestionTrial> qs = allQuestions.getOrDefault(section.getId(), List.of());
                    return qs.isEmpty() ? null : qs.getFirst();
                })
                .filter(Objects::nonNull)
                .toList();

        String finalQuizId = "quiz-" + data.getChapter().getId() + "-final";

        Quiz finalQuiz = new Quiz();

        finalQuiz.setId(finalQuizId);
        finalQuiz.setTitle("Final Quiz");
        finalQuiz.setType(QuizType.FINAL);
        finalQuiz.setOrder(1);

        chapterRef.collection("quizzes").document(finalQuizId).set(finalQuiz).get();

        for (QuestionTrial q : finalQuestionTrials) {
            chapterRef.collection("quizzes")
                    .document(finalQuizId)
                    .collection("questions")
                    .document(q.getId())
                    .set(q)
                    .get();
        }
    }
    // todo: make it skill based
    // global review quiz. One question per chapter
    private void seedReviewQuiz(Firestore db, List<ChapterData> chapters) throws Exception {

        List<QuestionTrial> reviewQuestionTrials = chapters.stream()
                .map(data -> {
                    List<QuestionTrial> qs = data.getQuestions()
                            .getOrDefault(data.getSections().getFirst().getId(), List.of()); // id of the first section of the chapter
                    return qs.isEmpty() ? null : qs.getFirst();
                })
                .filter(Objects::nonNull)
                .toList();

        String reviewQuizId = "quiz-review";

        Quiz reviewQuiz = new Quiz();
        reviewQuiz.setId(reviewQuizId);
        reviewQuiz.setTitle("Review Quiz");
        reviewQuiz.setType(QuizType.REVIEW);
        reviewQuiz.setOrder(1);

        db.collection("quizzes").document(reviewQuizId).set(reviewQuiz).get();

        for (QuestionTrial q : reviewQuestionTrials) {
            db.collection("quizzes")
                    .document(reviewQuizId)
                    .collection("questions")
                    .document(q.getId())
                    .set(q)
                    .get();
        }

        System.out.println("Seeded review quiz.");
    }

    // deletes documents in a collection, including subcollections

    private void deleteCollection(CollectionReference colRef) throws Exception {
        for (var doc : colRef.get().get().getDocuments()) {
            deleteDocumentRecursively(doc.getReference());
        }
    }
    // deletes documents and all of the subcollections, must delete the subcollections first and then the document
    private void deleteDocumentRecursively(DocumentReference docRef) throws Exception {
        for (CollectionReference col : docRef.listCollections()) {
            deleteCollection(col);
        }
        docRef.delete().get();
    }
}