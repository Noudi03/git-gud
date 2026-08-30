package com.app.gitgud.service;

import com.app.gitgud.mapper.ChapterFirestoreMapper;
import com.app.gitgud.model.Chapter;
import com.app.gitgud.repository.TutorialFirestoreRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {

    private final TutorialFirestoreRepository tutorialFirestoreRepository;
    private final ChapterFirestoreMapper chapterFirestoreMapper;


    public ChapterService(
            TutorialFirestoreRepository tutorialFirestoreRepository,
            ChapterFirestoreMapper chapterFirestoreMapper
    ) {

        this.tutorialFirestoreRepository =
                tutorialFirestoreRepository;

        this.chapterFirestoreMapper =
                chapterFirestoreMapper;
    }


    public List<Chapter> getChapters()
            throws Exception {

        QuerySnapshot snapshot =
                tutorialFirestoreRepository
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
                tutorialFirestoreRepository
                        .getChapter(chapterId)
                        .get()
                        .get();

        return chapterFirestoreMapper.toChapter(
                document
        );
    }
}
