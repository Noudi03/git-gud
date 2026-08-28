package com.app.gitgud.service;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Section;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TutorialService {

    private final ChapterService chapterService;
    private final SectionService sectionService;

    public TutorialService(
            ChapterService chapterService,
            SectionService sectionService
    ) {
        this.chapterService = chapterService;
        this.sectionService = sectionService;
    }

    public Map<String, List<Section>> getTutorialNavigation(
            List<Chapter> chapters
    ) throws Exception {

        Map<String, List<Section>> sectionsByChapter = new HashMap<>();

        for (Chapter chapter : chapters) {

            List<Section> sections =
                    sectionService.getSections(chapter.getId());

            sectionsByChapter.put(
                    chapter.getId(),
                    sections
            );
        }

        return sectionsByChapter;
    }

    public List<Chapter> getTutorialChapters() throws Exception {

        return chapterService.getChapters();
    }

    public Section getTutorialSection(
            String chapterId,
            String sectionId
    ) throws Exception {

        return sectionService.getSection(
                chapterId,
                sectionId
        );
    }

    public Chapter getTutorialChapter(
            String chapterId
    ) throws Exception {

        return chapterService.getChapter(chapterId);
    }
}
