package com.app.gitgud.service;

import com.app.gitgud.model.Chapter;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {

    private final Firestore db;

    public ChapterService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<Chapter> getChapters() throws Exception {

        return db.collection("chapters")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Chapter.class);
    }

    public Chapter getChapter(String chapterId) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .get()
                .get()
                .toObject(Chapter.class);
    }
}
