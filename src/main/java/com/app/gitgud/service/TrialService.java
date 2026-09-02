package com.app.gitgud.service;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.Trial;
import com.app.gitgud.repository.FirestoreTrialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrialService {

    private final FirestoreTrialRepository trialRepository;

    public TrialService(
            FirestoreTrialRepository trialRepository
    ) {

        this.trialRepository =
                trialRepository;
    }

    public List<Trial> getQuizTrials(
            Quiz quiz
    ) throws Exception {

        return trialRepository.findTrials(
                quiz
        );
    }

    public Trial getQuizTrial(
            Quiz quiz,
            String trialId
    ) throws Exception {

        return trialRepository.findTrial(
                quiz,
                trialId
        );
    }
}
