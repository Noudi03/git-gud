package com.app.gitgud.service;

import com.app.gitgud.mapper.ChapterMapper;
import com.app.gitgud.mapper.QuizMapper;
import com.app.gitgud.mapper.SectionMapper;
import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.User;
import com.app.gitgud.model.dto.ChapterDto;
import com.app.gitgud.model.dto.QuizDto;
import com.app.gitgud.model.dto.SectionDto;
import com.app.gitgud.model.dto.TutorialPageDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    private final CurriculumAccessService curriculumAccessService;

    public TutorialPageService(
            ChapterService chapterService,
            SectionService sectionService,
            QuizService quizService,
            MarkdownService markdownService,
            ChapterMapper chapterMapper,
            SectionMapper sectionMapper,
            QuizMapper quizMapper,
            CurriculumAccessService curriculumAccessService
    ) {
        this.chapterService = chapterService;
        this.sectionService = sectionService;
        this.quizService = quizService;
        this.markdownService = markdownService;
        this.chapterMapper = chapterMapper;
        this.sectionMapper = sectionMapper;
        this.quizMapper = quizMapper;
        this.curriculumAccessService = curriculumAccessService;
    }

    public TutorialPageDto getTutorialPage(
            User user
    ) throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(chapters);

        CurriculumProgressService progress =
                curriculumAccessService.loadProgress(
                        user.getId()
                );

        CurriculumAccessService.CurriculumData curriculumData =
                curriculumAccessService.loadCurriculumData(
                        chapters,
                        sectionsByChapter
                );

        return buildPage(
                user,
                chapters,
                sectionsByChapter,
                curriculumData,
                null,
                null,
                List.of(),
                progress
        );
    }

    public TutorialPageDto getTutorialPage(
            String chapterId,
            User user
    ) throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

        Map<String, List<Section>> sectionsByChapter =
                getSectionsByChapter(chapters);

        Chapter chapter =
                findChapter(
                        chapters,
                        chapterId
                );

        if (chapter == null) {
            return null;
        }

        CurriculumProgressService progress =
                curriculumAccessService.loadProgress(
                        user.getId()
                );

        CurriculumAccessService.CurriculumData curriculumData =
                curriculumAccessService.loadCurriculumData(
                        chapters,
                        sectionsByChapter
                );

        if (
                !curriculumAccessService.isChapterContentUnlocked(
                        user.getId(),
                        chapterId,
                        curriculumData,
                        progress
                )
        ) {
            throw new CurriculumAccessDeniedException(
                    "Chapter content is locked"
            );
        }

        return buildPage(
                user,
                chapters,
                sectionsByChapter,
                curriculumData,
                convertChapter(chapter),
                null,
                List.of(),
                progress
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
                getSectionsByChapter(chapters);

        Chapter chapter =
                findChapter(
                        chapters,
                        chapterId
                );

        if (chapter == null) {
            return null;
        }

        Section section =
                findSection(
                        sectionsByChapter.getOrDefault(
                                chapterId,
                                List.of()
                        ),
                        sectionId
                );

        if (section == null) {
            return null;
        }

        CurriculumProgressService progress =
                curriculumAccessService.loadProgress(
                        user.getId()
                );

        CurriculumAccessService.CurriculumData curriculumData =
                curriculumAccessService.loadCurriculumData(
                        chapters,
                        sectionsByChapter
                );

        if (
                !curriculumAccessService.isSectionContentUnlocked(
                        user.getId(),
                        chapterId,
                        sectionId,
                        curriculumData,
                        progress
                )
        ) {
            throw new CurriculumAccessDeniedException(
                    "Section content is locked"
            );
        }

        List<Quiz> quizzes =
                curriculumData.sectionQuizzes()
                        .getOrDefault(
                                new CurriculumAccessService.SectionKey(
                                        chapterId,
                                        sectionId
                                ),
                                List.of()
                        );

        return buildPage(
                user,
                chapters,
                sectionsByChapter,
                curriculumData,
                convertChapter(chapter),
                convertSection(section),
                quizMapper.toDtoList(quizzes),
                progress
        );
    }

    private TutorialPageDto buildPage(
            User user,
            List<Chapter> chapters,
            Map<String, List<Section>> sectionsByChapter,
            CurriculumAccessService.CurriculumData curriculumData,
            ChapterDto chapter,
            SectionDto section,
            List<QuizDto> quizzes,
            CurriculumProgressService progress
    ) {

        Map<String, Boolean>
                chapterContentUnlocked =
                new LinkedHashMap<>();

        Map<String, Boolean>
                chapterQuizUnlocked =
                new LinkedHashMap<>();

        Map<String, Map<String, Boolean>>
                sectionContentUnlocked =
                new LinkedHashMap<>();

        Map<String, QuizDto>
                chapterFinalQuizzes =
                new LinkedHashMap<>();

        for (Chapter currentChapter : chapters) {

            String currentChapterId =
                    currentChapter.getId();

            boolean chapterUnlocked =
                    curriculumAccessService.isChapterContentUnlocked(
                            user.getId(),
                            currentChapterId,
                            curriculumData,
                            progress
                    );

            chapterContentUnlocked.put(
                    currentChapterId,
                    chapterUnlocked
            );

            boolean finalQuizUnlocked =
                    curriculumAccessService.isChapterQuizUnlocked(
                            user.getId(),
                            currentChapterId,
                            curriculumData,
                            progress
                    );

            chapterQuizUnlocked.put(
                    currentChapterId,
                    finalQuizUnlocked
            );

            Map<String, Boolean> sectionAccess =
                    new LinkedHashMap<>();

            List<Section> sections =
                    sectionsByChapter.getOrDefault(
                            currentChapterId,
                            List.of()
                    );

            for (Section currentSection : sections) {

                sectionAccess.put(
                        currentSection.getId(),
                        curriculumAccessService.isSectionContentUnlocked(
                                user.getId(),
                                currentChapterId,
                                currentSection.getId(),
                                curriculumData,
                                progress
                        )
                );
            }

            sectionContentUnlocked.put(
                    currentChapterId,
                    sectionAccess
            );

            Quiz chapterFinalQuiz =
                    curriculumAccessService.getSingleChapterQuiz(
                            curriculumData,
                            currentChapterId
                    );

            chapterFinalQuizzes.put(
                    currentChapterId,
                    quizMapper.toDto(
                            chapterFinalQuiz
                    )
            );
        }

        Quiz introductoryQuiz =
                curriculumData.introductoryQuiz();

        boolean introductoryQuizAttempted =
                curriculumAccessService.hasAttemptForQuiz(
                        introductoryQuiz,
                        progress
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

    private Chapter findChapter(
            List<Chapter> chapters,
            String chapterId
    ) {

        return chapters.stream()
                .filter(
                        chapter ->
                                chapter.getId().equals(
                                        chapterId
                                )
                )
                .findFirst()
                .orElse(null);
    }

    private Section findSection(
            List<Section> sections,
            String sectionId
    ) {

        return sections.stream()
                .filter(
                        section ->
                                section.getId().equals(
                                        sectionId
                                )
                )
                .findFirst()
                .orElse(null);
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
