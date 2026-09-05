package com.app.gitgud.service;

import com.app.gitgud.mapper.ChapterMapper;
import com.app.gitgud.mapper.QuizMapper;
import com.app.gitgud.mapper.SectionMapper;
import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizType;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.User;
import com.app.gitgud.model.dto.ChapterDto;
import com.app.gitgud.model.dto.SectionDto;
import com.app.gitgud.model.dto.TutorialPageDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private final CurriculumContentAccessService
            curriculumContentAccessService;

    private final CurriculumAccessService
            curriculumAccessService;

    public TutorialPageService(
            ChapterService chapterService,
            SectionService sectionService,
            QuizService quizService,
            MarkdownService markdownService,
            ChapterMapper chapterMapper,
            SectionMapper sectionMapper,
            QuizMapper quizMapper,
            CurriculumContentAccessService
                    curriculumContentAccessService,
            CurriculumAccessService
                    curriculumAccessService
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

        this.curriculumContentAccessService =
                curriculumContentAccessService;

        this.curriculumAccessService =
                curriculumAccessService;
    }

    public TutorialPageDto getTutorialPage(
            User user
    ) throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(
                        chapters
                );

        return buildPage(
                user,
                chapters,
                sectionsByChapter,
                null,
                null,
                List.of()
        );
    }

    public TutorialPageDto getTutorialPage(
            String chapterId,
            User user
    ) throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(
                        chapters
                );

        Chapter chapter =
                chapterService.getChapter(
                        chapterId
                );

        if (chapter == null) {
            return null;
        }

        if (
                !curriculumContentAccessService
                        .isChapterContentUnlocked(
                                user.getId(),
                                chapterId
                        )
        ) {
            throw new CurriculumAccessDeniedException(
                    "Chapter content is locked"
            );
        }

        /*
         * Chapter pages contain ONLY chapter content.
         *
         * The chapter final quiz is represented in the
         * sidebar instead.
         */
        return buildPage(
                user,
                chapters,
                sectionsByChapter,
                convertChapter(chapter),
                null,
                List.of()
        );
    }

    public TutorialPageDto getTutorialPage(
            String chapterId,
            String sectionId,
            User user
    ) throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(
                        chapters
                );

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

        if (
                !curriculumContentAccessService
                        .isSectionContentUnlocked(
                                user.getId(),
                                chapterId,
                                sectionId
                        )
        ) {
            throw new CurriculumAccessDeniedException(
                    "Section content is locked"
            );
        }

        List<Quiz> quizzes =
                quizService.getSectionQuizzes(
                        chapterId,
                        sectionId
                );

        return buildPage(
                user,
                chapters,
                sectionsByChapter,
                convertChapter(chapter),
                convertSection(section),
                quizMapper.toDtoList(quizzes)
        );
    }

    private TutorialPageDto buildPage(
            User user,
            List<Chapter> chapters,
            Map<String, List<Section>>
                    sectionsByChapter,
            ChapterDto chapter,
            SectionDto section,
            List<com.app.gitgud.model.dto.QuizDto>
                    quizzes
    ) throws Exception {

        Map<String, Boolean>
                chapterContentUnlocked =
                new LinkedHashMap<>();

        Map<String, Boolean>
                chapterQuizUnlocked =
                new LinkedHashMap<>();

        Map<String, Map<String, Boolean>>
                sectionContentUnlocked =
                new LinkedHashMap<>();

        Map<String, com.app.gitgud.model.dto.QuizDto>
                chapterFinalQuizzes =
                new LinkedHashMap<>();

        for (Chapter currentChapter : chapters) {

            boolean chapterUnlocked =
                    curriculumContentAccessService
                            .isChapterContentUnlocked(
                                    user.getId(),
                                    currentChapter.getId()
                            );

            chapterContentUnlocked.put(
                    currentChapter.getId(),
                    chapterUnlocked
            );

            boolean finalQuizUnlocked =
                    curriculumAccessService
                            .isChapterQuizUnlocked(
                                    user.getId(),
                                    currentChapter.getId()
                            );

            chapterQuizUnlocked.put(
                    currentChapter.getId(),
                    finalQuizUnlocked
            );

            Map<String, Boolean>
                    sectionAccess =
                    new LinkedHashMap<>();

            List<Section> sections =
                    sectionsByChapter.getOrDefault(
                            currentChapter.getId(),
                            List.of()
                    );

            for (Section currentSection : sections) {

                sectionAccess.put(
                        currentSection.getId(),
                        curriculumContentAccessService
                                .isSectionContentUnlocked(
                                        user.getId(),
                                        currentChapter.getId(),
                                        currentSection.getId()
                                )
                );
            }

            sectionContentUnlocked.put(
                    currentChapter.getId(),
                    sectionAccess
            );

            Quiz chapterFinalQuiz =
                    curriculumAccessService
                            .getSingleChapterQuiz(
                                    currentChapter.getId()
                            );

            chapterFinalQuizzes.put(
                    currentChapter.getId(),
                    quizMapper.toDto(
                            chapterFinalQuiz
                    )
            );
        }

        Quiz introductoryQuiz =
                getIntroductoryQuiz();

        boolean introductoryQuizAttempted =
                curriculumAccessService
                        .hasAttemptForQuiz(
                                user.getId(),
                                introductoryQuiz
                        );

        return new TutorialPageDto(
                chapterMapper.toDtoList(chapters),
                convertSectionsByChapter(
                        sectionsByChapter
                ),
                chapterContentUnlocked,
                chapterQuizUnlocked,
                sectionContentUnlocked,
                quizMapper.toDto(
                        introductoryQuiz
                ),
                introductoryQuizAttempted,
                chapterFinalQuizzes,
                chapter,
                section,
                quizzes
        );
    }

    private Quiz getIntroductoryQuiz()
            throws Exception {

        List<Quiz> quizzes =
                quizService.getGlobalQuizzes();

        return quizzes.stream()
                .filter(quiz ->
                        quiz.getType()
                                == QuizType.INTRODUCTORY
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Introductory quiz not found"
                        )
                );
    }

    private List<Chapter> getOrderedChapters()
            throws Exception {

        return chapterService.getChapters()
                .stream()
                .sorted(
                        Comparator.comparingInt(
                                Chapter::getOrder
                        )
                )
                .toList();
    }

    private Map<String, List<Section>>
    getSectionsByChapter(
            List<Chapter> chapters
    ) throws Exception {

        Map<String, List<Section>>
                sectionsByChapter =
                new LinkedHashMap<>();

        for (Chapter chapter : chapters) {

            List<Section> sections =
                    sectionService.getSections(
                                    chapter.getId()
                            )
                            .stream()
                            .sorted(
                                    Comparator.comparingInt(
                                            Section::getOrder
                                    )
                            )
                            .toList();

            sectionsByChapter.put(
                    chapter.getId(),
                    sections
            );
        }

        return sectionsByChapter;
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

    private Map<String, List<SectionDto>>
    convertSectionsByChapter(
            Map<String, List<Section>>
                    sectionsByChapter
    ) {

        Map<String, List<SectionDto>>
                result =
                new LinkedHashMap<>();

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
