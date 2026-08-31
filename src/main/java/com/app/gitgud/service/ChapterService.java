package com.app.gitgud.service;

import com.app.gitgud.mapper.ChapterFirestoreMapper;
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
    private final ChapterFirestoreMapper chapterFirestoreMapper;


    public ChapterService(
            FirestoreTutorialRepository firestoreTutorialRepository,
            ChapterFirestoreMapper chapterFirestoreMapper
    ) {

        this.firestoreTutorialRepository =
                firestoreTutorialRepository;

        this.chapterFirestoreMapper =
                chapterFirestoreMapper;
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
                    chapterFirestoreMapper.toChapter(
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

        return chapterFirestoreMapper.toChapter(
                document
        );
    }
}
