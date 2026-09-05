package com.app.gitgud.model.dto;

import java.util.List;
import java.util.Map;

public class TutorialPageDto {

    private final List<ChapterDto> chapters;

    private final Map<String, List<SectionDto>>
            sectionsByChapter;

    private final Map<String, Boolean>
            chapterContentUnlocked;

    private final Map<String, Boolean>
            chapterQuizUnlocked;

    private final Map<String, Map<String, Boolean>>
            sectionContentUnlocked;

    private final QuizDto introductoryQuiz;

    private final boolean introductoryQuizAttempted;

    private final Map<String, QuizDto>
            chapterFinalQuizzes;

    private final ChapterDto chapter;
    private final SectionDto section;
    private final List<QuizDto> quizzes;

    public TutorialPageDto(
            List<ChapterDto> chapters,
            Map<String, List<SectionDto>> sectionsByChapter,
            Map<String, Boolean> chapterContentUnlocked,
            Map<String, Boolean> chapterQuizUnlocked,
            Map<String, Map<String, Boolean>> sectionContentUnlocked,
            QuizDto introductoryQuiz,
            boolean introductoryQuizAttempted,
            Map<String, QuizDto> chapterFinalQuizzes,
            ChapterDto chapter,
            SectionDto section,
            List<QuizDto> quizzes
    ) {

        this.chapters = chapters;
        this.sectionsByChapter = sectionsByChapter;
        this.chapterContentUnlocked =
                chapterContentUnlocked;
        this.chapterQuizUnlocked =
                chapterQuizUnlocked;
        this.sectionContentUnlocked =
                sectionContentUnlocked;
        this.introductoryQuiz =
                introductoryQuiz;
        this.introductoryQuizAttempted =
                introductoryQuizAttempted;
        this.chapterFinalQuizzes =
                chapterFinalQuizzes;
        this.chapter = chapter;
        this.section = section;
        this.quizzes = quizzes;
    }

    public List<ChapterDto> getChapters() {
        return chapters;
    }

    public Map<String, List<SectionDto>>
    getSectionsByChapter() {
        return sectionsByChapter;
    }

    public Map<String, Boolean>
    getChapterContentUnlocked() {
        return chapterContentUnlocked;
    }

    public Map<String, Boolean>
    getChapterQuizUnlocked() {
        return chapterQuizUnlocked;
    }

    public Map<String, Map<String, Boolean>>
    getSectionContentUnlocked() {
        return sectionContentUnlocked;
    }

    public QuizDto getIntroductoryQuiz() {
        return introductoryQuiz;
    }

    public boolean isIntroductoryQuizAttempted() {
        return introductoryQuizAttempted;
    }

    public Map<String, QuizDto>
    getChapterFinalQuizzes() {
        return chapterFinalQuizzes;
    }

    public ChapterDto getChapter() {
        return chapter;
    }

    public SectionDto getSection() {
        return section;
    }

    public List<QuizDto> getQuizzes() {
        return quizzes;
    }
}
