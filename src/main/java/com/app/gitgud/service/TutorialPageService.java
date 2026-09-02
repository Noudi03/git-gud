package com.app.gitgud.service;

import com.app.gitgud.mapper.ChapterMapper;
import com.app.gitgud.mapper.QuizMapper;
import com.app.gitgud.mapper.SectionMapper;
import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.dto.ChapterDto;
import com.app.gitgud.model.dto.SectionDto;
import com.app.gitgud.model.dto.TutorialPageDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TutorialPageService {

    private final ChapterService chapterService;
    private final SectionService sectionService;
    private final QuizService quizService;
    private final MarkdownService markdownService;

    private final ChapterMapper chapterMapper;
    private final SectionMapper sectionMapper;
    private final QuizMapper quizMapper;

    public TutorialPageService(
            ChapterService chapterService,
            SectionService sectionService,
            QuizService quizService,
            MarkdownService markdownService,
            ChapterMapper chapterMapper,
            SectionMapper sectionMapper,
            QuizMapper quizMapper
    ) {
        this.chapterService =
                chapterService;

        this.sectionService =
                sectionService;

        this.quizService =
                quizService;

        this.markdownService =
                markdownService;

        this.chapterMapper =
                chapterMapper;

        this.sectionMapper =
                sectionMapper;

        this.quizMapper =
                quizMapper;
    }

    public TutorialPageDto getTutorialPage()
            throws Exception {

        List<Chapter> chapters =
                chapterService.getChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(chapters);

        return new TutorialPageDto(
                chapterMapper.toDtoList(chapters),
                convertSectionsByChapter(
                        sectionsByChapter
                ),
                null,
                null,
                List.of()
        );
    }

    public TutorialPageDto getTutorialPage(
            String chapterId
    ) throws Exception {

        List<Chapter> chapters =
                chapterService.getChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(chapters);

        Chapter chapter =
                chapterService.getChapter(
                        chapterId
                );

        if (chapter == null) {
            return null;
        }

        List<Quiz> quizzes =
                quizService.getChapterQuizzes(
                        chapterId
                );

        return new TutorialPageDto(
                chapterMapper.toDtoList(chapters),
                convertSectionsByChapter(
                        sectionsByChapter
                ),
                convertChapter(chapter),
                null,
                quizMapper.toDtoList(quizzes)
        );
    }

    public TutorialPageDto getTutorialPage(
            String chapterId,
            String sectionId
    ) throws Exception {

        List<Chapter> chapters =
                chapterService.getChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(chapters);

        Chapter chapter =
                chapterService.getChapter(
                        chapterId
                );

        if (chapter == null) {
            return null;
        }

        Section section =
                sectionService.getSection(
                        chapterId,
                        sectionId
                );

        if (section == null) {
            return null;
        }

        List<Quiz> quizzes =
                quizService.getSectionQuizzes(
                        chapterId,
                        sectionId
                );

        return new TutorialPageDto(
                chapterMapper.toDtoList(chapters),
                convertSectionsByChapter(
                        sectionsByChapter
                ),
                convertChapter(chapter),
                convertSection(section),
                quizMapper.toDtoList(quizzes)
        );
    }

    private ChapterDto convertChapter(
            Chapter chapter
    ) {

        ChapterDto dto =
                chapterMapper.toDto(chapter);

        return new ChapterDto(
                dto.getId(),
                dto.getTitle(),
                markdownService.toHtml(
                        dto.getContent()
                ),
                dto.getOrder()
        );
    }

    private Map<String, List<Section>> getSectionsByChapter(
            List<Chapter> chapters
    ) throws Exception {

        Map<String, List<Section>> sectionsByChapter =
                new HashMap<>();

        for (Chapter chapter : chapters) {

            List<Section> sections =
                    sectionService.getSections(
                            chapter.getId()
                    );

            sectionsByChapter.put(
                    chapter.getId(),
                    sections
            );
        }

        return sectionsByChapter;
    }

    private Map<String, List<SectionDto>>
    convertSectionsByChapter(
            Map<String, List<Section>> sectionsByChapter
    ) {

        Map<String, List<SectionDto>>
                result =
                new HashMap<>();

        for (
                Map.Entry<String, List<Section>> entry :
                sectionsByChapter.entrySet()
        ) {

            result.put(
                    entry.getKey(),
                    convertSections(
                            entry.getValue()
                    )
            );
        }

        return result;
    }

    private List<SectionDto> convertSections(
            List<Section> sections
    ) {

        return sections.stream()
                .map(this::convertSection)
                .toList();
    }

    private SectionDto convertSection(
            Section section
    ) {

        SectionDto dto =
                sectionMapper.toDto(section);

        return new SectionDto(
                dto.getId(),
                dto.getTitle(),
                markdownService.toHtml(
                        dto.getContent()
                ),
                dto.getOrder(),
                markdownService.toHtml(
                        dto.getAdvancedContent()
                )
        );
    }
}
