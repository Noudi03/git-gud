package com.app.gitgud.service;

import com.app.gitgud.mapper.FirestoreChapterMapper;
import com.app.gitgud.model.Chapter;
import com.app.gitgud.repository.FirestoreTutorialRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {

    private final FirestoreTutorialRepository firestoreTutorialRepository;
    private final FirestoreChapterMapper firestoreChapterMapper;


    public ChapterService(
            FirestoreTutorialRepository firestoreTutorialRepository,
            FirestoreChapterMapper firestoreChapterMapper
    ) {

        this.firestoreTutorialRepository =
                firestoreTutorialRepository;

        this.firestoreChapterMapper =
                firestoreChapterMapper;
    }


    public List<Chapter> getChapters()
            throws Exception {

        QuerySnapshot snapshot =
                firestoreTutorialRepository
                        .getChaptersCollection()
                        .orderBy("order")
                        .get()
                        .get();

        List<Chapter> chapters =
                new ArrayList<>();

        for (
                DocumentSnapshot document :
                snapshot.getDocuments()
        ) {

            Chapter chapter =
                    firestoreChapterMapper.toChapter(
                            document
                    );

            if (chapter != null) {
                chapters.add(chapter);
            }
        }

        return chapters;
    }


    public Chapter getChapter(
            String chapterId
    ) throws Exception {

        DocumentSnapshot document =
                firestoreTutorialRepository
                        .getChapter(chapterId)
                        .get()
                        .get();

        return firestoreChapterMapper.toChapter(
                document
        );
    }
}
