package com.app.gitgud.service;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Section;
import com.app.gitgud.repository.FirestoreChapterRepository;
import com.app.gitgud.repository.FirestoreSectionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CurriculumContentAccessService {

    private final FirestoreChapterRepository chapterRepository;
    private final FirestoreSectionRepository sectionRepository;
    private final CurriculumAccessService curriculumAccessService;

    public CurriculumContentAccessService(
            FirestoreChapterRepository chapterRepository,
            FirestoreSectionRepository sectionRepository,
            CurriculumAccessService curriculumAccessService
    ) {

        this.chapterRepository =
                chapterRepository;

        this.sectionRepository =
                sectionRepository;

        this.curriculumAccessService =
                curriculumAccessService;
    }

    public boolean isChapterContentUnlocked(
            String userId,
            String chapterId
    ) throws Exception {

        CurriculumProgressService progress =
                curriculumAccessService.loadProgress(
                        userId
                );

        return isChapterContentUnlocked(
                userId,
                chapterId,
                progress
        );
    }

    public boolean isChapterContentUnlocked(
            String userId,
            String chapterId,
            CurriculumProgressService progress
    ) throws Exception {

        List<Chapter> chapters =
                chapterRepository.findChapters();

        boolean chapterExists =
                chapters.stream()
                        .anyMatch(
                                chapter ->
                                        chapter.getId()
                                                .equals(
                                                        chapterId
                                                )
                        );

        if (!chapterExists) {
            return false;
        }

        List<Section> sections =
                sectionRepository.findSections(
                                chapterId
                        )
                        .stream()
                        .sorted(
                                Comparator.comparingInt(
                                        Section::getOrder
                                )
                        )
                        .toList();

        if (sections.isEmpty()) {
            return false;
        }

        Section firstSection =
                sections.get(0);

        return curriculumAccessService
                .isSectionUnlocked(
                        userId,
                        chapterId,
                        firstSection.getId(),
                        progress
                );
    }

    public boolean isSectionContentUnlocked(
            String userId,
            String chapterId,
            String sectionId
    ) throws Exception {

        CurriculumProgressService progress =
                curriculumAccessService.loadProgress(
                        userId
                );

        return isSectionContentUnlocked(
                userId,
                chapterId,
                sectionId,
                progress
        );
    }

    public boolean isSectionContentUnlocked(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumProgressService progress
    ) throws Exception {

        return curriculumAccessService.isSectionUnlocked(
                userId,
                chapterId,
                sectionId,
                progress
        );
    }
}
