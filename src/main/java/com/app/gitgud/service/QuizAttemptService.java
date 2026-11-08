package com.app.gitgud.service;

import com.app.gitgud.mapper.QuizAttemptMapper;
import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.repository.FirestoreQuizAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizAttemptService {

    private final FirestoreQuizAttemptRepository quizAttemptRepository;
    private final QuizAttemptMapper quizAttemptMapper;
    private final QuizService quizService;

    public QuizAttemptService(
            FirestoreQuizAttemptRepository quizAttemptRepository,
            QuizAttemptMapper quizAttemptMapper,
            QuizService quizService
    ) {
        this.quizAttemptRepository =
                quizAttemptRepository;

        this.quizAttemptMapper =
                quizAttemptMapper;

        this.quizService =
                quizService;
    }

    public QuizAttempt createAttempt(
            String userId,
            Quiz quiz,
            String quizId
    ) throws Exception {

        if (quiz == null) {
            throw new IllegalArgumentException(
                    "Quiz not found"
            );
        }

        if (
                quizId == null
                        || quiz.getId() == null
                        || !quiz.getId().equals(quizId)
        ) {
            throw new IllegalArgumentException(
                    "Quiz ID does not match request path"
            );
        }

        String attemptId =
                UUID.randomUUID().toString();

        QuizAttempt attempt =
                quizAttemptMapper.toModel(
                        attemptId,
                        quiz
                );

        if (attempt == null) {
            throw new IllegalStateException(
                    "Could not create quiz attempt"
            );
        }

        if (attempt.getQuizId() == null) {
            throw new IllegalStateException(
                    "Quiz attempt has no quiz ID"
            );
        }

        quizAttemptRepository.createAttempt(
                userId,
                attempt
        );

        return attempt;
    }

    public List<QuizAttempt> getAttempts(
            String userId
    ) throws Exception {

        return quizAttemptRepository.findAttempts(
                userId
        );
    }

    public QuizAttempt getAttempt(
            String userId,
            String attemptId
    ) throws Exception {

        QuizAttempt attempt =
                quizAttemptRepository.findAttempt(
                        userId,
                        attemptId
                );

        if (attempt == null) {
            throw new IllegalArgumentException(
                    "Quiz attempt not found"
            );
        }

        return attempt;
    }

    public Quiz getQuiz(
            QuizAttempt attempt
    ) throws Exception {

        if (attempt == null) {
            throw new IllegalArgumentException(
                    "Quiz attempt is null"
            );
        }

        if (attempt.getQuizId() == null) {
            throw new IllegalStateException(
                    "Quiz attempt has no quiz ID: "
                            + attempt.getId()
            );
        }

        QuizContext context =
                attempt.getContext();

        if (context == null) {
            throw new IllegalStateException(
                    "Quiz attempt has no context: "
                            + attempt.getId()
            );
        }

        Quiz quiz;

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            quiz =
                    quizService.getSectionQuiz(
                            sectionContext.getChapterId(),
                            sectionContext.getSectionId(),
                            attempt.getQuizId()
                    );

        } else if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            quiz =
                    quizService.getChapterQuiz(
                            chapterContext.getChapterId(),
                            attempt.getQuizId()
                    );

        } else if (
                context instanceof GlobalQuizContext
        ) {

            quiz =
                    quizService.getGlobalQuiz(
                            attempt.getQuizId()
                    );

        } else {

            throw new IllegalArgumentException(
                    "Unsupported quiz context: "
                            + context.getClass().getName()
            );
        }

        if (quiz == null) {
            throw new IllegalStateException(
                    "Quiz not found for attempt "
                            + attempt.getId()
                            + ", quizId="
                            + attempt.getQuizId()
            );
        }

        return quiz;
    }
}