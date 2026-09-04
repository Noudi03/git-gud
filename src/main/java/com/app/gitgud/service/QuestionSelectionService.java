package com.app.gitgud.service;

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

    public QuestionSelectionService(
            FirestoreQuestionRepository questionRepository,
            QuestionSelectionStrategy selectionStrategy
    ) {

        this.questionRepository =
                questionRepository;

        this.selectionStrategy =
                selectionStrategy;
    }

    public List<Question> selectQuestions(
            User user,
            Quiz quiz
    ) throws Exception {

        List<Question> questions =
                questionRepository.findQuestions(
                        quiz
                );

        return selectionStrategy.selectQuestions(
                user,
                questions
        );
    }
}
