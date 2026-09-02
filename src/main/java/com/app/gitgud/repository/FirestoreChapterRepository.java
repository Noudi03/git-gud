package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreChapterMapper;
import com.app.gitgud.model.Chapter;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestoreChapterRepository {

    private final Firestore db;
    private final FirestoreChapterMapper mapper;


    public FirestoreChapterRepository(
            Firestore db,
            FirestoreChapterMapper mapper
    ) {

        this.db = db;
        this.mapper = mapper;
    }


    public List<Chapter> findChapters()
            throws Exception {

        return getChaptersCollection()
                .orderBy("order")
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(mapper::toModel)
                .filter(chapter -> chapter != null)
                .toList();
    }


    public Chapter findChapter(
            String chapterId
    ) throws Exception {

        return mapper.toModel(
                getChapter(chapterId)
                        .get()
                        .get()
        );
    }


    private CollectionReference getChaptersCollection() {

        return db.collection("chapters");
    }


    private DocumentReference getChapter(
            String chapterId
    ) {

        return getChaptersCollection()
                .document(chapterId);
    }
}
