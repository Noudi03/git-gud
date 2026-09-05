package com.app.gitgud.service;

import com.app.gitgud.config.GitGudProperties;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.User;
import com.app.gitgud.repository.FirestoreQuestionRepository;
import com.app.gitgud.service.selection.QuestionSelectionStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionSelectionService {

    private final FirestoreQuestionRepository questionRepository;
    private final QuestionSelectionStrategy selectionStrategy;
    private final GitGudProperties properties;

    public QuestionSelectionService(
            FirestoreQuestionRepository questionRepository,
            QuestionSelectionStrategy selectionStrategy,
            GitGudProperties properties
    ) {

        this.questionRepository =
                questionRepository;

        this.selectionStrategy =
                selectionStrategy;

        this.properties =
                properties;
    }

    public List<Question> selectQuestions(
            User user,
            Quiz quiz
    ) throws Exception {

        List<Question> questions =
                questionRepository.findQuestions(
                        quiz
                );

        int questionCount =
                properties
                        .getQuiz()
                        .getQuestionsPerAttempt();

        if (questionCount <= 0) {

            throw new IllegalStateException(
                    "Questions per quiz must be greater than zero"
            );
        }

        if (
                questions == null ||
                        questions.size() < questionCount
        ) {

            throw new IllegalStateException(
                    "Quiz does not contain enough questions"
            );
        }

        List<Question> selectedQuestions =
                selectionStrategy.selectQuestions(
                        user,
                        questions,
                        questionCount
                );

        if (
                selectedQuestions.size()
                        != questionCount
        ) {

            throw new IllegalStateException(
                    "Question selection must return exactly "
                            + questionCount
                            + " questions"
            );
        }

        return selectedQuestions;
    }
}
