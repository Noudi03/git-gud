package com.app.gitgud.repository;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class TutorialFirestoreRepository {

    private final Firestore db;


    public TutorialFirestoreRepository(
            Firestore db
    ) {

        this.db = db;
    }


    public CollectionReference getChaptersCollection() {

        return db.collection("chapters");
    }


    public DocumentReference getChapter(
            String chapterId
    ) {

        return getChaptersCollection()
                .document(chapterId);
    }


    public CollectionReference getSectionsCollection(
            String chapterId
    ) {

        return getChapter(chapterId)
                .collection("sections");
    }


    public DocumentReference getSection(
            String chapterId,
            String sectionId
    ) {

        return getSectionsCollection(chapterId)
                .document(sectionId);
    }
}
