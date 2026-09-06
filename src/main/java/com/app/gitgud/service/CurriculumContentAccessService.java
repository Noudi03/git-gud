package com.app.gitgud.service;

import org.springframework.stereotype.Service;

@Service
public class CurriculumContentAccessService {

    private final CurriculumAccessService curriculumAccessService;

    public CurriculumContentAccessService(
            CurriculumAccessService curriculumAccessService
    ) {
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

        CurriculumAccessService.CurriculumData data =
                curriculumAccessService.loadCurriculumData();

        return isChapterContentUnlocked(
                userId,
                chapterId,
                data,
                progress
        );
    }

    public boolean isChapterContentUnlocked(
            String userId,
            String chapterId,
            CurriculumProgressService progress
    ) throws Exception {

        CurriculumAccessService.CurriculumData data =
                curriculumAccessService.loadCurriculumData();

        return isChapterContentUnlocked(
                userId,
                chapterId,
                data,
                progress
        );
    }

    public boolean isChapterContentUnlocked(
            String userId,
            String chapterId,
            CurriculumAccessService.CurriculumData data,
            CurriculumProgressService progress
    ) {

        return curriculumAccessService.isChapterContentUnlocked(
                userId,
                chapterId,
                data,
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

        CurriculumAccessService.CurriculumData data =
                curriculumAccessService.loadCurriculumData();

        return isSectionContentUnlocked(
                userId,
                chapterId,
                sectionId,
                data,
                progress
        );
    }

    public boolean isSectionContentUnlocked(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumProgressService progress
    ) throws Exception {

        CurriculumAccessService.CurriculumData data =
                curriculumAccessService.loadCurriculumData();

        return isSectionContentUnlocked(
                userId,
                chapterId,
                sectionId,
                data,
                progress
        );
    }

    public boolean isSectionContentUnlocked(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumAccessService.CurriculumData data,
            CurriculumProgressService progress
    ) {

        return curriculumAccessService.isSectionContentUnlocked(
                userId,
                chapterId,
                sectionId,
                data,
                progress
        );
    }
}
