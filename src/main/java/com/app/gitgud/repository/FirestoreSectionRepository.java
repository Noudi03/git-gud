package com.app.gitgud.repository;

import com.app.gitgud.mapper.FirestoreSectionMapper;
import com.app.gitgud.model.Section;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestoreSectionRepository {

    private final Firestore db;
    private final FirestoreSectionMapper mapper;


    public FirestoreSectionRepository(
            Firestore db,
            FirestoreSectionMapper mapper
    ) {

        this.db = db;
        this.mapper = mapper;
    }


    public List<Section> findSections(
            String chapterId
    ) throws Exception {

        return getSectionsCollection(chapterId)
                .orderBy("order")
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(document ->
                        mapper.toModel(
                                document,
                                chapterId
                        )
                )
                .filter(section -> section != null)
                .toList();
    }


    public Section findSection(
            String chapterId,
            String sectionId
    ) throws Exception {

        return mapper.toModel(
                getSection(
                        chapterId,
                        sectionId
                )
                        .get()
                        .get(),
                chapterId
        );
    }


    private CollectionReference getSectionsCollection(
            String chapterId
    ) {

        return db.collection("chapters")
                .document(chapterId)
                .collection("sections");
    }


    private DocumentReference getSection(
            String chapterId,
            String sectionId
    ) {

        return getSectionsCollection(chapterId)
                .document(sectionId);
    }
}
