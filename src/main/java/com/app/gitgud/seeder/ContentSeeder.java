package com.app.gitgud.seeder;

import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.Section;
import com.app.gitgud.seeder.data.chapter1.Chapter1Data;
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
import java.util.stream.Collectors;

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
                new Chapter1Data()
                // new Chapter2Data(),
                // new Chapter3Data()
        );

        for (ChapterData data : chapters) {
            seedChapter(db, data);
            System.out.println("Seeded chapter: " + data.getChapter().getTitle());
        }

        System.out.println("Seeding completed.");
    }

    private void seedChapter(Firestore db, ChapterData data) throws Exception {
        // Save chapter document
        DocumentReference chapterRef = db.collection("chapters").document(data.getChapter().getId());
        chapterRef.set(data.getChapter()).get();

        Map<String, List<Question>> allQuestions = data.getQuestions();

        for (Section section : data.getSections()) {
            // Save section document
            chapterRef.collection("sections")
                    .document(section.getId())
                    .set(section)
                    .get();

            List<Question> questions = allQuestions.getOrDefault(section.getId(), List.of());

            //Save questions as subcollection under section
            for (Question q : questions) {
                chapterRef.collection("sections")
                        .document(section.getId())
                        .collection("questions")
                        .document(q.getId())
                        .set(q)
                        .get();
            }

            //create mini quiz for this section
            if (!questions.isEmpty()) {
                List<String> questionIds = questions.stream()
                        .map(Question::getId)
                        .collect(Collectors.toList());

                Quiz miniQuiz = new Quiz();
                miniQuiz.setId("quiz-" + section.getId());
                miniQuiz.setChapterId(data.getChapter().getId());
                miniQuiz.setSectionId(section.getId());
                miniQuiz.setType("mini");
                miniQuiz.setQuestionIds(questionIds);

                db.collection("quizzes")
                        .document(miniQuiz.getId())
                        .set(miniQuiz)
                        .get();
            }
        }

        // create tge final quiz for the chapter
        // takes the first question from each section
        // TODO: replace with skill based generation of questions
        List<String> finalQuestionIds = data.getSections().stream()
                .map(section -> {
                    List<Question> qs = allQuestions.getOrDefault(section.getId(), List.of());
                    return qs.isEmpty() ? null : qs.get(0).getId();
                })
                .filter(id -> id != null)
                .collect(Collectors.toList());

        Quiz finalQuiz = new Quiz();
        finalQuiz.setId("quiz-" + data.getChapter().getId() +  "-final");
        finalQuiz.setChapterId(data.getChapter().getId());
        finalQuiz.setSectionId(null);
        finalQuiz.setType("final");
        finalQuiz.setQuestionIds(finalQuestionIds);

        db.collection("quizzes")
                .document(finalQuiz.getId())
                .set(finalQuiz)
                .get();
    }

    // Review quiz, called after all chapters are seeded
    // Cross-chapter: takes one question from each chapter's first section

    private void seedReviewQuiz(Firestore db, List<ChapterData> chapters) throws Exception {
        List<String> reviewQuestionIds = chapters.stream()
                .map(data -> {
                    List<Question> qs = data.getQuestions()
                            .getOrDefault(data.getSections().get(0).getId(), List.of());
                    return qs.isEmpty() ? null : qs.get(0).getId();
                })
                .filter(id -> id != null)
                .collect(Collectors.toList());

        Quiz reviewQuiz = new Quiz();
        reviewQuiz.setId("quiz-review");
        reviewQuiz.setChapterId(null);
        reviewQuiz.setSectionId(null);
        reviewQuiz.setType("review");
        reviewQuiz.setQuestionIds(reviewQuestionIds);

        db.collection("quizzes")
                .document(reviewQuiz.getId())
                .set(reviewQuiz)
                .get();

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