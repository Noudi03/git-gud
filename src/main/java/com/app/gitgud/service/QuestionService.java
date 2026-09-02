package com.app.gitgud.service;

import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.repository.FirestoreQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final FirestoreQuestionRepository questionRepository;


    public QuestionService(
            FirestoreQuestionRepository questionRepository
    ) {

        this.questionRepository =
                questionRepository;
    }


    public List<Question> getQuizQuestions(
            Quiz quiz
    ) throws Exception {

        return questionRepository.findQuestions(
                quiz
        );
    }


    public Question getQuizQuestion(
            Quiz quiz,
            String questionId
    ) throws Exception {

        return questionRepository.findQuestion(
                quiz,
                questionId
        );
    }
}
