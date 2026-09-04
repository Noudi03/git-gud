package com.app.gitgud.service;

import com.app.gitgud.model.Chapter;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizGrade;
import com.app.gitgud.model.Section;
import com.app.gitgud.model.QuizType;
import com.app.gitgud.repository.FirestoreChapterRepository;
import com.app.gitgud.repository.FirestoreSectionRepository;
import org.springframework.stereotype.Service;

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
                chapterRepository.findChapters();

        Chapter chapter =
                findChapter(
                        chapters,
                        chapterId
                );

        if (chapter == null) {
            return false;
        }

        List<Section> sections =
                sectionRepository.findSections(
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

            return hasPassedIntroductoryQuiz(
                    userId
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
                    previousSection.getId()
            );
        }

        Chapter previousChapter =
                chapters.get(
                        chapterIndex - 1
                );

        List<Section> previousSections =
                sectionRepository.findSections(
                        previousChapter.getId()
                );

        if (previousSections.isEmpty()) {

            return hasPassedChapterQuiz(
                    userId,
                    previousChapter.getId()
            );
        }

        Section lastSection =
                previousSections.get(
                        previousSections.size() - 1
                );

        return hasPassedSectionQuiz(
                userId,
                previousChapter.getId(),
                lastSection.getId()
        );
    }


    public boolean isChapterUnlocked(
            String userId,
            String chapterId
    ) throws Exception {

        List<Chapter> chapters =
                chapterRepository.findChapters();

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


    public boolean hasPassedQuiz(
            String userId,
            Quiz quiz
    ) throws Exception {

        List<QuizAttempt> attempts =
                quizAttemptService.getAttempts(
                        userId
                );

        for (QuizAttempt attempt : attempts) {

            if (!attempt.getQuizId().equals(
                    quiz.getId()
            )) {
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

            if (grade != QuizGrade.FAILURE) {
                return true;
            }
        }

        return false;
    }


    private boolean hasPassedIntroductoryQuiz(
            String userId
    ) throws Exception {

        List<Quiz> quizzes =
                quizService.getGlobalQuizzes();

        for (Quiz quiz : quizzes) {

            if (
                    quiz.getType()
                            == QuizType.INTRODUCTORY
            ) {

                return hasPassedQuiz(
                        userId,
                        quiz
                );
            }
        }

        return false;
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

        return quizzes.get(0);
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

        return quizzes.get(0);
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
