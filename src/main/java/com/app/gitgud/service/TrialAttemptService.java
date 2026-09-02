package com.app.gitgud.service;

import com.app.gitgud.mapper.TrialAttemptMapper;
import com.app.gitgud.model.TrialAttempt;
import com.app.gitgud.model.dto.TrialAttemptRequestDto;
import com.app.gitgud.repository.FirestoreTrialAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrialAttemptService {

    private final FirestoreTrialAttemptRepository trialAttemptRepository;
    private final TrialAttemptMapper trialAttemptMapper;

    public TrialAttemptService(
            FirestoreTrialAttemptRepository trialAttemptRepository,
            TrialAttemptMapper trialAttemptMapper
    ) {

        this.trialAttemptRepository =
                trialAttemptRepository;

        this.trialAttemptMapper =
                trialAttemptMapper;
    }

    public void createTrialAttempt(
            String userId,
            String attemptId,
            TrialAttemptRequestDto request
    ) throws Exception {

        TrialAttempt trialAttempt =
                trialAttemptMapper.toModel(
                        request
                );

        trialAttemptRepository.createTrialAttempt(
                userId,
                attemptId,
                trialAttempt
        );
    }

    public List<TrialAttempt> getTrialAttempts(
            String userId,
            String attemptId
    ) throws Exception {

        return trialAttemptRepository
                .findTrialAttempts(
                        userId,
                        attemptId
                );
    }

    public TrialAttempt getTrialAttempt(
            String userId,
            String attemptId,
            String trialId
    ) throws Exception {

        return trialAttemptRepository
                .findTrialAttempt(
                        userId,
                        attemptId,
                        trialId
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Trial attempt not found"
                        )
                );
    }
}
