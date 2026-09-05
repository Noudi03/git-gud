package com.app.gitgud.service;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizGrade;
import com.app.gitgud.model.QuizType;
import com.app.gitgud.model.Section;
import com.app.gitgud.repository.FirestoreChapterRepository;
import com.app.gitgud.repository.FirestoreSectionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CurriculumAccessService {

    private final FirestoreChapterRepository chapterRepository;
    private final FirestoreSectionRepository sectionRepository;
    private final QuizService quizService;
    private final QuizAttemptService quizAttemptService;
    private final QuizResultService quizResultService;

    public CurriculumAccessService(
            FirestoreChapterRepository chapterRepository,
            FirestoreSectionRepository sectionRepository,
            QuizService quizService,
            QuizAttemptService quizAttemptService,
            QuizResultService quizResultService
    ) {

        this.chapterRepository =
                chapterRepository;

        this.sectionRepository =
                sectionRepository;

        this.quizService =
                quizService;

        this.quizAttemptService =
                quizAttemptService;

        this.quizResultService =
                quizResultService;
    }

    public boolean isSectionUnlocked(
            String userId,
            String chapterId,
            String sectionId
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

        /*
         * First section of first chapter:
         * unlocked after passing the introductory quiz.
         */
        if (
                chapterIndex == 0
                        && sectionIndex == 0
        ) {

            return hasPassedIntroductoryQuiz(
                    userId
            );
        }

        /*
         * Every later section:
         * unlocked after passing the previous section quiz.
         */
        if (sectionIndex > 0) {

            Section previousSection =
                    sections.get(
                            sectionIndex - 1
                    );

            return hasPassedSectionQuiz(
                    userId,
                    chapterId,
                    previousSection.getId()
            );
        }

        /*
         * First section of a later chapter:
         * unlocked after passing the previous chapter's
         * final quiz.
         */
        Chapter previousChapter =
                chapters.get(
                        chapterIndex - 1
                );

        return hasPassedChapterQuiz(
                userId,
                previousChapter.getId()
        );
    }

    /*
     * This means the chapter is available in the curriculum
     * progression sense.
     *
     * Chapter CONTENT should use isSectionUnlocked() for the
     * chapter's first section instead.
     */
    public boolean isChapterUnlocked(
            String userId,
            String chapterId
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

            return hasPassedIntroductoryQuiz(
                    userId
            );
        }

        Chapter previousChapter =
                chapters.get(
                        chapterIndex - 1
                );

        return hasPassedChapterQuiz(
                userId,
                previousChapter.getId()
        );
    }

    /*
     * The chapter final quiz becomes available only after
     * every section in that chapter has been passed.
     */
    public boolean isChapterQuizUnlocked(
            String userId,
            String chapterId
    ) throws Exception {

        if (
                !isChapterUnlocked(
                        userId,
                        chapterId
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
                            section.getId()
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
                lastChapter.getId()
        );
    }

    public boolean hasPassedQuiz(
            String userId,
            Quiz quiz
    ) throws Exception {

        if (quiz == null) {
            return false;
        }

        List<QuizAttempt> attempts =
                quizAttemptService.getAttempts(
                        userId
                );

        for (QuizAttempt attempt : attempts) {

            if (
                    !attempt.getQuizId()
                            .equals(quiz.getId())
            ) {
                continue;
            }

            if (
                    attempt.getStatus()
                            != QuizAttemptStatus.COMPLETED
            ) {
                continue;
            }

            QuizGrade grade =
                    quizResultService
                            .getResult(
                                    userId,
                                    attempt.getId()
                            )
                            .getGrade();

            if (
                    grade != QuizGrade.FAILURE
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

        if (quiz == null) {
            return false;
        }

        List<QuizAttempt> attempts =
                quizAttemptService.getAttempts(
                        userId
                );

        return attempts.stream()
                .anyMatch(attempt ->
                        attempt.getQuizId()
                                .equals(quiz.getId())
                );
    }

    public boolean hasPreviousAttemptForQuiz(
            String userId,
            Quiz quiz,
            String currentAttemptId
    ) throws Exception {

        if (quiz == null) {
            return false;
        }

        List<QuizAttempt> attempts =
                quizAttemptService.getAttempts(
                        userId
                );

        return attempts.stream()
                .anyMatch(attempt ->
                        !attempt.getId().equals(
                                currentAttemptId
                        )
                                && attempt.getQuizId()
                                .equals(quiz.getId())
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

    private boolean hasPassedIntroductoryQuiz(
            String userId
    ) throws Exception {

        List<Quiz> quizzes =
                quizService.getGlobalQuizzes();

        Quiz introductoryQuiz =
                quizzes.stream()
                        .filter(quiz ->
                                quiz.getType()
                                        == QuizType.INTRODUCTORY
                        )
                        .findFirst()
                        .orElse(null);

        return hasPassedQuiz(
                userId,
                introductoryQuiz
        );
    }

    private boolean hasPassedSectionQuiz(
            String userId,
            String chapterId,
            String sectionId
    ) throws Exception {

        Quiz quiz =
                getSingleSectionQuiz(
                        chapterId,
                        sectionId
                );

        return hasPassedQuiz(
                userId,
                quiz
        );
    }

    private boolean hasPassedChapterQuiz(
            String userId,
            String chapterId
    ) throws Exception {

        Quiz quiz =
                getSingleChapterQuiz(
                        chapterId
                );

        return hasPassedQuiz(
                userId,
                quiz
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
                .filter(chapter ->
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
                .filter(section ->
                        section.getId().equals(
                                sectionId
                        )
                )
                .findFirst()
                .orElse(null);
    }
}
