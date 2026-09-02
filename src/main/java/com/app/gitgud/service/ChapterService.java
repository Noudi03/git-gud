package com.app.gitgud.service;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.repository.FirestoreChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {

    private final FirestoreChapterRepository chapterRepository;


    public ChapterService(
            FirestoreChapterRepository chapterRepository
    ) {

        this.chapterRepository =
                chapterRepository;
    }


    public List<Chapter> getChapters()
            throws Exception {

        return chapterRepository.findChapters();
    }


    public Chapter getChapter(
            String chapterId
    ) throws Exception {

        return chapterRepository.findChapter(
                chapterId
        );
    }
}
