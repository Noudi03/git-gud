package com.app.gitgud.service;

import com.app.gitgud.mapper.QuizAttemptMapper;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.dto.QuizAttemptRequestDto;
import com.app.gitgud.repository.FirestoreQuizAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizAttemptService {

    private final FirestoreQuizAttemptRepository quizAttemptRepository;
    private final QuizAttemptMapper quizAttemptMapper;

    public QuizAttemptService(
            FirestoreQuizAttemptRepository quizAttemptRepository,
            QuizAttemptMapper quizAttemptMapper
    ) {
        this.quizAttemptRepository =
                quizAttemptRepository;

        this.quizAttemptMapper =
                quizAttemptMapper;
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

        if (!quiz.getId().equals(request.getQuizId())) {
            throw new IllegalArgumentException(
                    "Quiz ID does not match request path"
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
        return quizAttemptRepository.findAttempts(userId);
    }

    public QuizAttempt getAttempt(
            String userId,
            String attemptId
    ) throws Exception {
        return quizAttemptRepository
                .findAttempt(
                        userId,
                        attemptId
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Quiz attempt not found"
                        )
                );
    }
}
