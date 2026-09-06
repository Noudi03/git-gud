package com.app.gitgud.service;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.QuizGrade;
import com.app.gitgud.model.QuizType;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.repository.FirestoreChapterRepository;
import com.app.gitgud.repository.FirestoreSectionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CurriculumAccessService {

    private final FirestoreChapterRepository chapterRepository;
    private final FirestoreSectionRepository sectionRepository;
    private final QuizService quizService;
    private final QuizAttemptService quizAttemptService;

    public CurriculumAccessService(
            FirestoreChapterRepository chapterRepository,
            FirestoreSectionRepository sectionRepository,
            QuizService quizService,
            QuizAttemptService quizAttemptService
    ) {
        this.chapterRepository = chapterRepository;
        this.sectionRepository = sectionRepository;
        this.quizService = quizService;
        this.quizAttemptService = quizAttemptService;
    }

    public CurriculumProgressService loadProgress(
            String userId
    ) throws Exception {

        return new CurriculumProgressService(
                quizAttemptService.getAttempts(userId)
        );
    }

    public CurriculumData loadCurriculumData()
            throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

        Map<String, List<Section>> sectionsByChapter =
                getOrderedSectionsByChapter(
                        chapters
                );

        return loadCurriculumData(
                chapters,
                sectionsByChapter
        );
    }

    public CurriculumData loadCurriculumData(
            List<Chapter> chapters,
            Map<String, List<Section>> sectionsByChapter
    ) throws Exception {

        Map<SectionKey, List<Quiz>> sectionQuizzes =
                new LinkedHashMap<>();

        Map<String, List<Quiz>> chapterQuizzes =
                new LinkedHashMap<>();

        for (Chapter chapter : chapters) {

            String chapterId =
                    chapter.getId();

            List<Section> sections =
                    sectionsByChapter.getOrDefault(
                            chapterId,
                            List.of()
                    );

            for (Section section : sections) {

                List<Quiz> quizzes =
                        quizService.getSectionQuizzes(
                                chapterId,
                                section.getId()
                        );

                sectionQuizzes.put(
                        new SectionKey(
                                chapterId,
                                section.getId()
                        ),
                        quizzes
                );
            }

            chapterQuizzes.put(
                    chapterId,
                    quizService.getChapterQuizzes(
                            chapterId
                    )
            );
        }

        Quiz introductoryQuiz =
                quizService.getGlobalQuizzes()
                        .stream()
                        .filter(
                                quiz ->
                                        quiz.getType()
                                                == QuizType.INTRODUCTORY
                        )
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "Introductory quiz not found"
                                        )
                        );

        return new CurriculumData(
                chapters,
                sectionsByChapter,
                sectionQuizzes,
                chapterQuizzes,
                introductoryQuiz
        );
    }

    public boolean isSectionUnlocked(
            String userId,
            String chapterId,
            String sectionId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        CurriculumData data =
                loadCurriculumData();

        return isSectionUnlocked(
                userId,
                chapterId,
                sectionId,
                data,
                progress
        );
    }

    public boolean isSectionUnlocked(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumProgressService progress
    ) throws Exception {

        CurriculumData data =
                loadCurriculumData();

        return isSectionUnlocked(
                userId,
                chapterId,
                sectionId,
                data,
                progress
        );
    }

    public boolean isSectionUnlocked(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumData data,
            CurriculumProgressService progress
    ) {

        List<Chapter> chapters =
                data.chapters();

        Chapter chapter =
                findChapter(
                        chapters,
                        chapterId
                );

        if (chapter == null) {
            return false;
        }

        List<Section> sections =
                data.sectionsByChapter()
                        .getOrDefault(
                                chapterId,
                                List.of()
                        );

        Section section =
                findSection(
                        sections,
                        sectionId
                );

        if (section == null) {
            return false;
        }

        int chapterIndex =
                chapters.indexOf(chapter);

        int sectionIndex =
                sections.indexOf(section);

        if (
                chapterIndex == 0
                        && sectionIndex == 0
        ) {
            return hasAttemptForQuiz(
                    data.introductoryQuiz(),
                    progress
            );
        }

        if (sectionIndex > 0) {

            Section previousSection =
                    sections.get(
                            sectionIndex - 1
                    );

            return hasPassedSectionQuiz(
                    chapterId,
                    previousSection.getId(),
                    data,
                    progress
            );
        }

        Chapter previousChapter =
                chapters.get(
                        chapterIndex - 1
                );

        return hasPassedChapterQuiz(
                previousChapter.getId(),
                data,
                progress
        );
    }

    public boolean isChapterUnlocked(
            String userId,
            String chapterId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        CurriculumData data =
                loadCurriculumData();

        return isChapterUnlocked(
                userId,
                chapterId,
                data,
                progress
        );
    }

    public boolean isChapterUnlocked(
            String userId,
            String chapterId,
            CurriculumProgressService progress
    ) throws Exception {

        CurriculumData data =
                loadCurriculumData();

        return isChapterUnlocked(
                userId,
                chapterId,
                data,
                progress
        );
    }

    public boolean isChapterUnlocked(
            String userId,
            String chapterId,
            CurriculumData data,
            CurriculumProgressService progress
    ) {

        List<Chapter> chapters =
                data.chapters();

        Chapter chapter =
                findChapter(
                        chapters,
                        chapterId
                );

        if (chapter == null) {
            return false;
        }

        int chapterIndex =
                chapters.indexOf(chapter);

        if (chapterIndex == 0) {
            return hasAttemptForQuiz(
                    data.introductoryQuiz(),
                    progress
            );
        }

        Chapter previousChapter =
                chapters.get(
                        chapterIndex - 1
                );

        return hasPassedChapterQuiz(
                previousChapter.getId(),
                data,
                progress
        );
    }

    public boolean isChapterQuizUnlocked(
            String userId,
            String chapterId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        CurriculumData data =
                loadCurriculumData();

        return isChapterQuizUnlocked(
                userId,
                chapterId,
                data,
                progress
        );
    }

    public boolean isChapterQuizUnlocked(
            String userId,
            String chapterId,
            CurriculumProgressService progress
    ) throws Exception {

        CurriculumData data =
                loadCurriculumData();

        return isChapterQuizUnlocked(
                userId,
                chapterId,
                data,
                progress
        );
    }

    public boolean isChapterQuizUnlocked(
            String userId,
            String chapterId,
            CurriculumData data,
            CurriculumProgressService progress
    ) {

        if (
                !isChapterUnlocked(
                        userId,
                        chapterId,
                        data,
                        progress
                )
        ) {
            return false;
        }

        List<Section> sections =
                data.sectionsByChapter()
                        .getOrDefault(
                                chapterId,
                                List.of()
                        );

        if (sections.isEmpty()) {
            return false;
        }

        for (Section section : sections) {

            if (
                    !hasPassedSectionQuiz(
                            chapterId,
                            section.getId(),
                            data,
                            progress
                    )
            ) {
                return false;
            }
        }

        return true;
    }

    public boolean isCurriculumComplete(
            String userId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        CurriculumData data =
                loadCurriculumData();

        List<Chapter> chapters =
                data.chapters();

        if (chapters.isEmpty()) {
            return true;
        }

        Chapter lastChapter =
                chapters.get(
                        chapters.size() - 1
                );

        return hasPassedChapterQuiz(
                lastChapter.getId(),
                data,
                progress
        );
    }

    public boolean isChapterContentUnlocked(
            String userId,
            String chapterId,
            CurriculumData data,
            CurriculumProgressService progress
    ) {

        List<Chapter> chapters =
                data.chapters();

        Chapter chapter =
                findChapter(
                        chapters,
                        chapterId
                );

        if (chapter == null) {
            return false;
        }

        List<Section> sections =
                data.sectionsByChapter()
                        .getOrDefault(
                                chapterId,
                                List.of()
                        );

        if (sections.isEmpty()) {
            return false;
        }

        return isSectionUnlocked(
                userId,
                chapterId,
                sections.get(0).getId(),
                data,
                progress
        );
    }

    public boolean isSectionContentUnlocked(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumData data,
            CurriculumProgressService progress
    ) {

        return isSectionUnlocked(
                userId,
                chapterId,
                sectionId,
                data,
                progress
        );
    }

    public boolean hasPassedQuiz(
            String userId,
            Quiz quiz
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        return hasPassedQuiz(
                userId,
                quiz,
                progress
        );
    }

    public boolean hasPassedQuiz(
            String userId,
            Quiz quiz,
            CurriculumProgressService progress
    ) {

        if (quiz == null) {
            return false;
        }

        for (QuizAttempt attempt :
                progress.getAttempts()) {

            if (!isSameQuiz(attempt, quiz)) {
                continue;
            }

            if (
                    attempt.getStatus()
                            != QuizAttemptStatus.COMPLETED
            ) {
                continue;
            }

            QuizGrade grade =
                    attempt.getGrade();

            if (
                    grade != null
                            && grade != QuizGrade.FAILURE
            ) {
                return true;
            }
        }

        return false;
    }

    public boolean hasAttemptForQuiz(
            String userId,
            Quiz quiz
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        return hasAttemptForQuiz(
                quiz,
                progress
        );
    }

    public boolean hasAttemptForQuiz(
            Quiz quiz,
            CurriculumProgressService progress
    ) {

        if (quiz == null) {
            return false;
        }

        return progress.getAttempts()
                .stream()
                .anyMatch(
                        attempt ->
                                isSameQuiz(
                                        attempt,
                                        quiz
                                )
                );
    }

    public boolean hasPreviousAttemptForQuiz(
            String userId,
            Quiz quiz,
            String currentAttemptId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        return hasPreviousAttemptForQuiz(
                quiz,
                currentAttemptId,
                progress
        );
    }

    public boolean hasPreviousAttemptForQuiz(
            Quiz quiz,
            String currentAttemptId,
            CurriculumProgressService progress
    ) {

        if (quiz == null) {
            return false;
        }

        return progress.getAttempts()
                .stream()
                .anyMatch(
                        attempt ->
                                !Objects.equals(
                                        attempt.getId(),
                                        currentAttemptId
                                )
                                        && isSameQuiz(
                                        attempt,
                                        quiz
                                )
                );
    }

    public Quiz getSingleSectionQuiz(
            String chapterId,
            String sectionId
    ) throws Exception {

        CurriculumData data =
                loadCurriculumData();

        return getSingleSectionQuiz(
                data,
                chapterId,
                sectionId
        );
    }

    public Quiz getSingleSectionQuiz(
            CurriculumData data,
            String chapterId,
            String sectionId
    ) {

        List<Quiz> quizzes =
                data.sectionQuizzes()
                        .getOrDefault(
                                new SectionKey(
                                        chapterId,
                                        sectionId
                                ),
                                List.of()
                        );

        if (quizzes.size() != 1) {
            throw new IllegalStateException(
                    "Expected exactly one quiz for "
                            + "chapter "
                            + chapterId
                            + ", section "
                            + sectionId
            );
        }

        Quiz quiz =
                quizzes.get(0);

        validateCurriculumQuiz(
                quiz
        );

        return quiz;
    }

    public Quiz getSingleChapterQuiz(
            String chapterId
    ) throws Exception {

        CurriculumData data =
                loadCurriculumData();

        return getSingleChapterQuiz(
                data,
                chapterId
        );
    }

    public Quiz getSingleChapterQuiz(
            CurriculumData data,
            String chapterId
    ) {

        List<Quiz> quizzes =
                data.chapterQuizzes()
                        .getOrDefault(
                                chapterId,
                                List.of()
                        );

        if (quizzes.size() != 1) {
            throw new IllegalStateException(
                    "Expected exactly one quiz for chapter "
                            + chapterId
            );
        }

        Quiz quiz =
                quizzes.get(0);

        validateCurriculumQuiz(
                quiz
        );

        return quiz;
    }

    private boolean hasPassedSectionQuiz(
            String chapterId,
            String sectionId,
            CurriculumData data,
            CurriculumProgressService progress
    ) {

        Quiz quiz =
                getSingleSectionQuiz(
                        data,
                        chapterId,
                        sectionId
                );

        return hasPassedQuiz(
                null,
                quiz,
                progress
        );
    }

    private boolean hasPassedChapterQuiz(
            String chapterId,
            CurriculumData data,
            CurriculumProgressService progress
    ) {

        Quiz quiz =
                getSingleChapterQuiz(
                        data,
                        chapterId
                );

        return hasPassedQuiz(
                null,
                quiz,
                progress
        );
    }

    private List<Chapter> getOrderedChapters()
            throws Exception {

        return chapterRepository.findChapters()
                .stream()
                .sorted(
                        Comparator.comparingInt(
                                Chapter::getOrder
                        )
                )
                .toList();
    }

    private Map<String, List<Section>>
    getOrderedSectionsByChapter(
            List<Chapter> chapters
    ) throws Exception {

        Map<String, List<Section>>
                sectionsByChapter =
                new LinkedHashMap<>();

        for (Chapter chapter : chapters) {

            sectionsByChapter.put(
                    chapter.getId(),
                    sectionRepository.findSections(
                                    chapter.getId()
                            )
                            .stream()
                            .sorted(
                                    Comparator.comparingInt(
                                            Section::getOrder
                                    )
                            )
                            .toList()
            );
        }

        return sectionsByChapter;
    }

    private void validateCurriculumQuiz(
            Quiz quiz
    ) {

        if (quiz.getType() != null) {
            throw new IllegalStateException(
                    "Curriculum quiz must not have a quiz type"
            );
        }
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

    private boolean isSameQuiz(
            QuizAttempt attempt,
            Quiz quiz
    ) {

        if (attempt == null || quiz == null) {
            return false;
        }

        if (
                !Objects.equals(
                        quiz.getId(),
                        attempt.getQuizId()
                )
        ) {
            return false;
        }

        QuizContext quizContext =
                quiz.getContext();

        QuizContext attemptContext =
                attempt.getContext();

        if (
                quizContext instanceof SectionQuizContext
                        quizSectionContext
                        && attemptContext instanceof SectionQuizContext
                        attemptSectionContext
        ) {
            return Objects.equals(
                    quizSectionContext.getChapterId(),
                    attemptSectionContext.getChapterId()
            )
                    && Objects.equals(
                    quizSectionContext.getSectionId(),
                    attemptSectionContext.getSectionId()
            );
        }

        if (
                quizContext instanceof ChapterQuizContext
                        quizChapterContext
                        && attemptContext instanceof ChapterQuizContext
                        attemptChapterContext
        ) {
            return Objects.equals(
                    quizChapterContext.getChapterId(),
                    attemptChapterContext.getChapterId()
            );
        }

        if (
                quizContext instanceof GlobalQuizContext
                        && attemptContext instanceof GlobalQuizContext
        ) {
            return true;
        }

        return false;
    }

    public record SectionKey(
            String chapterId,
            String sectionId
    ) {
    }

    public record CurriculumData(
            List<Chapter> chapters,
            Map<String, List<Section>> sectionsByChapter,
            Map<SectionKey, List<Quiz>> sectionQuizzes,
            Map<String, List<Quiz>> chapterQuizzes,
            Quiz introductoryQuiz
    ) {
    }
}
