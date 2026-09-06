package com.app.gitgud.service;

import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizGrade;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CurriculumProgressService {

    private final List<QuizAttempt> attempts;

    public CurriculumProgressService(
            List<QuizAttempt> attempts
    ) {
        this.attempts =
                List.copyOf(attempts);
    }

    public List<QuizAttempt> getAttempts() {
        return Collections.unmodifiableList(
                attempts
        );
    }

    public QuizGrade getGrade(
            QuizAttempt attempt
    ) {
        if (attempt == null) {
            return null;
        }

        return attempt.getGrade();
    }
}
