package com.app.gitgud.service;

import com.app.gitgud.mapper.QuizAttemptMapper;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizAttemptStatus;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
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
            QuizAttemptRequestDto request
    ) throws Exception {

        if (quiz == null) {
            throw new IllegalArgumentException(
                    "Quiz not found"
            );
        }

        if (
                !quiz.getId().equals(
                        request.getQuizId()
                )
        ) {
            throw new IllegalArgumentException(
                    "Quiz ID does not match request path"
            );
        }

        if (
                !"in_progress".equalsIgnoreCase(
                        request.getStatus()
                )
        ) {
            throw new IllegalArgumentException(
                    "New quiz attempts must start as in_progress"
            );
        }

        String attemptId =
                UUID.randomUUID().toString();

        QuizAttempt attempt =
                quizAttemptMapper.toModel(
                        attemptId,
                        quiz,
                        request
                );

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


    public QuizAttempt finalizeAttempt(
            String userId,
            String attemptId,
            QuizAttemptStatus status
    ) throws Exception {

        if (
                status != QuizAttemptStatus.COMPLETED
                        && status != QuizAttemptStatus.CANCELLED
        ) {
            throw new IllegalArgumentException(
                    "Invalid final quiz attempt status"
            );
        }

        QuizAttempt attempt =
                getAttempt(
                        userId,
                        attemptId
                );

        if (
                attempt.getStatus()
                        != QuizAttemptStatus.IN_PROGRESS
        ) {
            throw new IllegalStateException(
                    "Quiz attempt has already been finalized"
            );
        }

        quizAttemptRepository.updateStatus(
                userId,
                attemptId,
                status.getValue()
        );

        return getAttempt(
                userId,
                attemptId
        );
    }


    public boolean markOutcomeProcessed(
            String userId,
            String attemptId
    ) throws Exception {

        return quizAttemptRepository
                .markOutcomeProcessed(
                        userId,
                        attemptId
                );
    }


    public Quiz getQuiz(
            QuizAttempt attempt
    ) throws Exception {

        QuizContext context =
                attempt.getContext();

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            return quizService.getSectionQuiz(
                    sectionContext.getChapterId(),
                    sectionContext.getSectionId(),
                    attempt.getQuizId()
            );
        }

        if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            return quizService.getChapterQuiz(
                    chapterContext.getChapterId(),
                    attempt.getQuizId()
            );
        }

        if (
                context instanceof GlobalQuizContext
        ) {

            return quizService.getGlobalQuiz(
                    attempt.getQuizId()
            );
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }
}
