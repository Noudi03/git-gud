package com.app.gitgud.service;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizType;
import com.app.gitgud.model.SectionQuizContext;
import org.springframework.stereotype.Service;

@Service
public class QuizAccessService {

    private final CurriculumAccessService curriculumAccessService;

    public QuizAccessService(
            CurriculumAccessService curriculumAccessService
    ) {

        this.curriculumAccessService =
                curriculumAccessService;
    }

    public void validateCanStartQuiz(
            String userId,
            Quiz quiz
    ) throws Exception {

        if (quiz == null) {
            throw new IllegalArgumentException(
                    "Quiz not found"
            );
        }

        if (
                quiz.getContext()
                        instanceof SectionQuizContext sectionContext
        ) {

            validateCurriculumQuiz(
                    quiz
            );

            if (
                    !curriculumAccessService
                            .isSectionUnlocked(
                                    userId,
                                    sectionContext.getChapterId(),
                                    sectionContext.getSectionId()
                            )
            ) {
                throw new CurriculumAccessDeniedException(
                        "Section quiz is locked"
                );
            }

            return;
        }

        if (
                quiz.getContext()
                        instanceof ChapterQuizContext chapterContext
        ) {

            validateCurriculumQuiz(
                    quiz
            );

            if (
                    !curriculumAccessService
                            .isChapterQuizUnlocked(
                                    userId,
                                    chapterContext.getChapterId()
                            )
            ) {
                throw new CurriculumAccessDeniedException(
                        "Chapter quiz is locked"
                );
            }

            return;
        }

        if (
                quiz.getContext()
                        instanceof GlobalQuizContext
        ) {

            if (
                    quiz.getType()
                            == QuizType.INTRODUCTORY
            ) {

                if (
                        curriculumAccessService
                                .hasAttemptForQuiz(
                                        userId,
                                        quiz
                                )
                ) {
                    throw new CurriculumAccessDeniedException(
                            "Introductory quiz has already been attempted"
                    );
                }

                return;
            }

            if (
                    quiz.getType()
                            == QuizType.REVIEW
            ) {

                if (
                        !curriculumAccessService
                                .isCurriculumComplete(
                                        userId
                                )
                ) {
                    throw new CurriculumAccessDeniedException(
                            "Review quizzes are locked"
                    );
                }

                return;
            }

            throw new IllegalStateException(
                    "Unsupported global quiz type"
            );
        }

        throw new IllegalStateException(
                "Unsupported quiz context"
        );
    }

    private void validateCurriculumQuiz(
            Quiz quiz
    ) {

        if (quiz.getType() != null) {
            throw new IllegalStateException(
                    "Curriculum quizzes must not have a quiz type"
            );
        }
    }
}
