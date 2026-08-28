package com.app.gitgud.service;

import com.app.gitgud.model.Section;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final Firestore db;

    public SectionService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<Section> getSections(String chapterId) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .collection("sections")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Section.class);
    }

    public Section getSection(
            String chapterId,
            String sectionId
    ) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .collection("sections")
                .document(sectionId)
                .get()
                .get()
                .toObject(Section.class);
    }
}
