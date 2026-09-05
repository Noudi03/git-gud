package com.app.gitgud.seeder;

import com.app.gitgud.model.*;
import com.app.gitgud.seeder.data.chapter1.Chapter1Data;
import com.app.gitgud.seeder.data.chapter2.Chapter2Data;
import com.app.gitgud.seeder.data.chapter3.Chapter3Data;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
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
                new Chapter2Data(),
                new Chapter3Data()
        );

        for (ChapterData data : chapters) {
            seedChapter(db, data);
            System.out.println("Seeded chapter: " + data.getChapter().getTitle());
        }
        seedReviewQuiz(db, chapters);
        System.out.println("Seeding completed.");
    }

    // quizzes are stored as subcollections within their parent section or parent chapter (except global quizzes)
    // questions are stored as a subcollection (under their quiz)
    private void seedChapter(Firestore db, ChapterData data) throws Exception {

        DocumentReference chapterRef = db.collection("chapters").document(data.getChapter().getId());
        chapterRef.set(data.getChapter()).get();

        Map<String, List<MultipleChoiceQuestion>> allMcq = data.getQuestions();
        Map<String, List<TrueFalseQuestion>> allTf = data.getTrueFalseQuestions();
        Map<String, List<FillInTheBlankQuestion>> allFib = data.getFillInTheBlankQuestions();

        for (Section section : data.getSections()) {

            DocumentReference sectionRef = chapterRef.collection("sections").document(section.getId());
            sectionRef.set(section).get();

            // Section-level practice quiz
            List<MultipleChoiceQuestion> mcqQuestions =
                    allMcq.getOrDefault(section.getId(), List.of());

            List<TrueFalseQuestion> tfQuestions =
                    allTf.getOrDefault(section.getId(), List.of());

            List<FillInTheBlankQuestion> fibQuestions =
                    allFib.getOrDefault(section.getId(), List.of());

            if (!mcqQuestions.isEmpty() || !tfQuestions.isEmpty() || !fibQuestions.isEmpty()) {
                String quizId = "quiz-practice";

                Quiz practiceQuiz = new Quiz();
                practiceQuiz.setId(quizId);
                practiceQuiz.setTitle("Practice Quiz");
                practiceQuiz.setOrder(1);

                sectionRef.collection("quizzes").document(quizId).set(practiceQuiz).get();

                // Multiple choice questions
                for (MultipleChoiceQuestion q : mcqQuestions) {
                    sectionRef.collection("quizzes")
                            .document(quizId)
                            .collection("questions")
                            .document(q.getId())
                            .set(toMcqData(q))
                            .get();
                }

                // True/false questions
                for (TrueFalseQuestion q : tfQuestions) {
                    sectionRef.collection("quizzes")
                            .document(quizId)
                            .collection("questions")
                            .document(q.getId())
                            .set(toTrueFalseData(q))
                            .get();
                }

                // Fill-in-the-blank questions
                for (FillInTheBlankQuestion q : fibQuestions) {
                    sectionRef.collection("quizzes")
                            .document(quizId)
                            .collection("questions")
                            .document(q.getId())
                            .set(toFillInTheBlankData(q))
                            .get();
                }
            }
        }

        // todo: make it skill based
        // Chapter-level final quiz: one MCQ per section
        List<MultipleChoiceQuestion> finalMcqQuestions = data.getSections().stream()
                .map(section -> {
                    List<MultipleChoiceQuestion> qs =
                            allMcq.getOrDefault(section.getId(), List.of());
                    return qs.isEmpty() ? null : qs.getFirst();
                })
                .filter(Objects::nonNull)
                .toList();

        String finalQuizId = "quiz-" + data.getChapter().getId() + "-final";

        Quiz finalQuiz = new Quiz();
        finalQuiz.setId(finalQuizId);
        finalQuiz.setTitle("Final Quiz");
        finalQuiz.setOrder(1);

        chapterRef.collection("quizzes").document(finalQuizId).set(finalQuiz).get();

        for (MultipleChoiceQuestion q : finalMcqQuestions) {
            chapterRef.collection("quizzes")
                    .document(finalQuizId)
                    .collection("questions")
                    .document(q.getId())
                    .set(toMcqData(q))
                    .get();
        }
    }

    // todo: make it skill based
    // global review quiz, one MCQ per chapter
    private void seedReviewQuiz(Firestore db, List<ChapterData> chapters) throws Exception {

        List<MultipleChoiceQuestion> reviewMcqQuestions = chapters.stream()
                .map(data -> {
                    List<MultipleChoiceQuestion> qs = data.getQuestions()
                            .getOrDefault(
                                    data.getSections().getFirst().getId(),
                                    List.of()
                            );
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

        for (MultipleChoiceQuestion q : reviewMcqQuestions) {
            db.collection("quizzes")
                    .document(reviewQuizId)
                    .collection("questions")
                    .document(q.getId())
                    .set(toMcqData(q))
                    .get();
        }

        System.out.println("Seeded review quiz.");
    }

    // Firestore serialisers
    private Map<String, Object> toMcqData(MultipleChoiceQuestion q) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", q.getType().getValue());
        data.put("text", q.getText());
        data.put("options", q.getOptions());
        data.put("correctAnswer", q.getCorrectAnswer());
        data.put("difficulty", q.getDifficulty().getValue());
        data.put("hints", q.getHints());
        return data;
    }

    private Map<String, Object> toTrueFalseData(TrueFalseQuestion q) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", q.getType().getValue());
        data.put("text", q.getText());
        data.put("correctAnswer", q.isCorrectAnswer());
        data.put("difficulty", q.getDifficulty().getValue());
        data.put("hints", q.getHints());
        return data;
    }

    private Map<String, Object> toFillInTheBlankData(FillInTheBlankQuestion q) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", q.getType().getValue());
        data.put("text", q.getText());
        data.put("correctAnswer", q.getCorrectAnswer());
        data.put("difficulty", q.getDifficulty().getValue());
        data.put("hints", q.getHints());
        return data;
    }

    // Firestore helpers
    // deletes documents in a collection, including subcollections
    private void deleteCollection(CollectionReference colRef) throws Exception {
        for (var doc : colRef.get().get().getDocuments()) {
            deleteDocumentRecursively(doc.getReference());
        }
    }

    // deletes document and all of its subcollections — subcollections must be deleted first
    private void deleteDocumentRecursively(DocumentReference docRef) throws Exception {
        for (CollectionReference col : docRef.listCollections()) {
            deleteCollection(col);
        }
        docRef.delete().get();
    }
}
