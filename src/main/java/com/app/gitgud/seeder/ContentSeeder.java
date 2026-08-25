package com.app.gitgud.seeder;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Section;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContentSeeder implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

//        if (!db.collection("chapters").get().get().isEmpty()){
//            System.out.println("DB already seeded");
//            return;
//        }

        //seedChapters(db);
        //seedSections(db);

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
        Section s1 = new Section("section1_1", "chapter1", "What is git?", "Placeholder", 1);
        Section s2 = new Section("section1_2", "chapter1", "First commands", "Placeholder", 2);

        for (Section s : List.of(s1, s2)) {
            CollectionReference chapters =  db.collection("chapters");
            DocumentReference chapterDoc = chapters.document(s.getChapterId());
            CollectionReference sections  = chapterDoc.collection("sections");
            DocumentReference sectionDoc = sections.document(s.getId());
            sectionDoc.set(s).get();
        }

    }

//    private void seedQuestions(Firestore db) throws Exception {
//
//    }
}
