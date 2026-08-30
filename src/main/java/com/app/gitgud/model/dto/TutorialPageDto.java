package com.app.gitgud.model.dto;

import java.util.List;
import java.util.Map;

public class TutorialPageDto {

    private final List<ChapterDto> chapters;
    private final Map<String, List<SectionDto>> sectionsByChapter;

    private final ChapterDto chapter;
    private final SectionDto section;
    private final List<QuizDto> quizzes;


    public TutorialPageDto(
            List<ChapterDto> chapters,
            Map<String, List<SectionDto>> sectionsByChapter,
            ChapterDto chapter,
            SectionDto section,
            List<QuizDto> quizzes
    ) {

        this.chapters = chapters;
        this.sectionsByChapter = sectionsByChapter;
        this.chapter = chapter;
        this.section = section;
        this.quizzes = quizzes;
    }


    public List<ChapterDto> getChapters() {
        return chapters;
    }

    public Map<String, List<SectionDto>> getSectionsByChapter() {
        return sectionsByChapter;
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
