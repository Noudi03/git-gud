package com.app.gitgud.service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Section;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class TutorialService {

    private final Firestore db;

    public TutorialService() {
        this.db = FirestoreClient.getFirestore();
    }

    public List<Chapter> getChapters() throws Exception {

        return db.collection("chapters")
                .orderBy("order")
                .get()
                .get()
                .toObjects(Chapter.class);
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

    public Section getSection(String chapterId, String sectionId) throws Exception {

        return db.collection("chapters")
                .document(chapterId)
                .collection("sections")
                .document(sectionId)
                .get()
                .get()
                .toObject(Section.class);
    }

    public Map<String, List<Section>> getSectionsByChapter(List<Chapter> chapters) throws Exception {

        Map<String, List<Section>> sectionsByChapter = new HashMap<>();

        for (Chapter chapter : chapters) {
            List<Section> sections = getSections(chapter.getId());
            sectionsByChapter.put(chapter.getId(), sections);
        }

        return sectionsByChapter;
    }
}
