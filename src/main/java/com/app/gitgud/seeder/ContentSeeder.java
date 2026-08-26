package com.app.gitgud.seeder;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.Section;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.text.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContentSeeder implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        if (!db.collection("chapters").get().get().isEmpty()){
            System.out.println("DB already seeded");
            return;
        }

        seedChapters(db);
        seedSections(db);
        seedQuestions(db);
        seedQuizzes(db);

    }

    private void seedChapters(Firestore db) throws Exception  {
        Chapter chapter1 = new Chapter("chapter1", "Intro to git", 1);
        Chapter chapter2 = new Chapter("chapter2", "Chapter 2 title", 2);
        Chapter chapter3 = new Chapter("chapter3", "Chapter 3 title", 3);

        db.collection("chapters").document(chapter1.getId()).set(chapter1).get();
        db.collection("chapters").document(chapter2.getId()).set(chapter2).get();
        db.collection("chapters").document(chapter3.getId()).set(chapter3).get();

    }

    private void seedSections(Firestore db) throws Exception {

        // ch 1
        Section s1 = new Section(
                "section1_1",
                "chapter1",
                "What is git?",
                "Placeholder",
                1,
                null,
                false,
                "Placeholder advanced content"
        );

        Section s2 = new Section(
                "section1_2",
                "chapter1",
                "First commands",
                "Placeholder",
                2,
                "section1_1",
                false,
                "Placeholder advanced content"
        );

        for (Section s : List.of(s1, s2)) {
            CollectionReference chapters =  db.collection("chapters");
            DocumentReference chapterDoc = chapters.document(s.getChapterId());
            CollectionReference sections  = chapterDoc.collection("sections");
            DocumentReference sectionDoc = sections.document(s.getId());
            sectionDoc.set(s).get();
        }

    }


    private void seedQuestions(Firestore db) throws Exception {
        List<String> sectionIds = List.of("section1_1", "section1_2");
        for (String sectionId : sectionIds) {
            String questionId = "q" + sectionId + "_1";
            Question q = new Question(questionId, "chapter1", sectionId, "placeholder question for " + sectionId, placeholderOptions(), "a", "hard", "Placeholder hint 1", "Placeholder hint 2");
            DocumentReference questionDoc = db.collection("questions").document(q.getId());
            questionDoc.set(q).get();
        }

    }

    private Map<String, String> placeholderOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("a", "Option A");
        options.put("b", "option B");
        options.put("c", "Option C");
        options.put("d", "Option D");
        return options;
    }
private void seedQuizzes(Firestore db) throws Exception {
        List <String> sectionIds = List.of("section1_1", "section1_2");
        for (String sectionId: sectionIds) {
            String questionId = "q_" + sectionId + "_1";
            Quiz quiz = new Quiz("quiz_" + sectionId, "chapter1", sectionId, "mini", List.of(questionId));

            DocumentReference quizDoc   = db.collection("quizzes").document(quiz.getId());
            quizDoc.set(quiz).get();
        }

        Quiz finalQuiz = new Quiz("quiz_chapter1_final", "chapter1", null,
                "final", List.of("q_section1_1_1", "q_section1_2_1"));

        DocumentReference finalQuizDoc = db.collection("quizzes").document(finalQuiz.getId());
        finalQuizDoc.set(finalQuiz).get();

        Quiz reviewQuiz = new Quiz("quiz_review", null, null, "review", List.of());
        DocumentReference reviewQuizDoc = db.collection("quizzes").document(reviewQuiz.getId());
        reviewQuizDoc.set(reviewQuiz).get();
}

}
