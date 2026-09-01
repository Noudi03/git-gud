package com.app.gitgud.service;

import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.repository.FirestoreQuizAttemptRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizAttemptService {

    private final FirestoreQuizAttemptRepository firestoreQuizAttemptRepository;


    public QuizAttemptService(
            FirestoreQuizAttemptRepository firestoreQuizAttemptRepository
    ) {

        this.firestoreQuizAttemptRepository =
                firestoreQuizAttemptRepository;
    }


    public void createAttempt(
            String userId,
            QuizAttempt attempt
    ) throws Exception {

        firestoreQuizAttemptRepository.createAttempt(
                userId,
                attempt
        );
    }
}
