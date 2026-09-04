package com.app.gitgud.service;

import com.app.gitgud.mapper.QuestionAttemptMapper;
import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.dto.QuestionAttemptRequestDto;
import com.app.gitgud.repository.FirestoreQuestionAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionAttemptService {

    private final FirestoreQuestionAttemptRepository questionAttemptRepository;
    private final QuestionAttemptMapper questionAttemptMapper;

    public QuestionAttemptService(
            FirestoreQuestionAttemptRepository questionAttemptRepository,
            QuestionAttemptMapper questionAttemptMapper
    ) {

        this.questionAttemptRepository =
                questionAttemptRepository;

        this.questionAttemptMapper =
                questionAttemptMapper;
    }

    public void createQuestionAttempt(
            String userId,
            String attemptId,
            QuestionAttemptRequestDto request
    ) throws Exception {

        String questionAttemptId =
                UUID.randomUUID().toString();

        QuestionAttempt questionAttempt =
                questionAttemptMapper.toModel(
                        questionAttemptId,
                        request
                );

        questionAttemptRepository.createQuestionAttempt(
                userId,
                attemptId,
                questionAttempt
        );
    }

    public List<QuestionAttempt> getQuestionAttempts(
            String userId,
            String attemptId
    ) throws Exception {

        return questionAttemptRepository
                .findQuestionAttempts(
                        userId,
                        attemptId
                );
    }

    public QuestionAttempt getQuestionAttempt(
            String userId,
            String attemptId,
            String questionId
    ) throws Exception {

        return questionAttemptRepository
                .findQuestionAttempt(
                        userId,
                        attemptId,
                        questionId
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Question attempt not found"
                        )
                );
    }
}
