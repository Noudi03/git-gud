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
import java.util.List;
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
        this.chapterRepository =
                chapterRepository;

        this.sectionRepository =
                sectionRepository;

        this.quizService =
                quizService;

        this.quizAttemptService =
                quizAttemptService;
    }

    public CurriculumProgressService loadProgress(
            String userId
    ) throws Exception {

        return new CurriculumProgressService(
                quizAttemptService.getAttempts(
                        userId
                )
        );
    }

    public boolean isSectionUnlocked(
            String userId,
            String chapterId,
            String sectionId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        return isSectionUnlocked(
                userId,
                chapterId,
                sectionId,
                progress
        );
    }

    public boolean isSectionUnlocked(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumProgressService progress
    ) throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

        Chapter chapter =
                findChapter(
                        chapters,
                        chapterId
                );

        if (chapter == null) {
            return false;
        }

        List<Section> sections =
                getOrderedSections(
                        chapterId
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
            return hasAttemptedIntroductoryQuiz(
                    progress
            );
        }

        if (sectionIndex > 0) {
            Section previousSection =
                    sections.get(
                            sectionIndex - 1
                    );

            return hasPassedSectionQuiz(
                    userId,
                    chapterId,
                    previousSection.getId(),
                    progress
            );
        }

        Chapter previousChapter =
                chapters.get(
                        chapterIndex - 1
                );

        return hasPassedChapterQuiz(
                userId,
                previousChapter.getId(),
                progress
        );
    }

    public boolean isChapterUnlocked(
            String userId,
            String chapterId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        return isChapterUnlocked(
                userId,
                chapterId,
                progress
        );
    }

    public boolean isChapterUnlocked(
            String userId,
            String chapterId,
            CurriculumProgressService progress
    ) throws Exception {

        List<Chapter> chapters =
                getOrderedChapters();

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
            return hasAttemptedIntroductoryQuiz(
                    progress
            );
        }

        Chapter previousChapter =
                chapters.get(
                        chapterIndex - 1
                );

        return hasPassedChapterQuiz(
                userId,
                previousChapter.getId(),
                progress
        );
    }

    public boolean isChapterQuizUnlocked(
            String userId,
            String chapterId
    ) throws Exception {

        CurriculumProgressService progress =
                loadProgress(userId);

        return isChapterQuizUnlocked(
                userId,
                chapterId,
                progress
        );
    }

    public boolean isChapterQuizUnlocked(
            String userId,
            String chapterId,
            CurriculumProgressService progress
    ) throws Exception {

        if (
                !isChapterUnlocked(
                        userId,
                        chapterId,
                        progress
                )
        ) {
            return false;
        }

        List<Section> sections =
                getOrderedSections(
                        chapterId
                );

        if (sections.isEmpty()) {
            return false;
        }

        for (Section section : sections) {
            if (
                    !hasPassedSectionQuiz(
                            userId,
                            chapterId,
                            section.getId(),
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

        List<Chapter> chapters =
                getOrderedChapters();

        if (chapters.isEmpty()) {
            return true;
        }

        Chapter lastChapter =
                chapters.get(
                        chapters.size() - 1
                );

        return hasPassedChapterQuiz(
                userId,
                lastChapter.getId(),
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

        List<Quiz> quizzes =
                quizService.getSectionQuizzes(
                        chapterId,
                        sectionId
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

        List<Quiz> quizzes =
                quizService.getChapterQuizzes(
                        chapterId
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

    private boolean hasAttemptedIntroductoryQuiz(
            CurriculumProgressService progress
    ) throws Exception {

        List<Quiz> quizzes =
                quizService.getGlobalQuizzes();

        Quiz introductoryQuiz =
                quizzes.stream()
                        .filter(
                                quiz ->
                                        quiz.getType()
                                                == QuizType.INTRODUCTORY
                        )
                        .findFirst()
                        .orElse(null);

        return hasAttemptForQuiz(
                introductoryQuiz,
                progress
        );
    }

    private boolean hasPassedSectionQuiz(
            String userId,
            String chapterId,
            String sectionId,
            CurriculumProgressService progress
    ) throws Exception {

        Quiz quiz =
                getSingleSectionQuiz(
                        chapterId,
                        sectionId
                );

        return hasPassedQuiz(
                userId,
                quiz,
                progress
        );
    }

    private boolean hasPassedChapterQuiz(
            String userId,
            String chapterId,
            CurriculumProgressService progress
    ) throws Exception {

        Quiz quiz =
                getSingleChapterQuiz(
                        chapterId
                );

        return hasPassedQuiz(
                userId,
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

    private List<Section> getOrderedSections(
            String chapterId
    ) throws Exception {

        return sectionRepository.findSections(
                        chapterId
                )
                .stream()
                .sorted(
                        Comparator.comparingInt(
                                Section::getOrder
                        )
                )
                .toList();
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
}
