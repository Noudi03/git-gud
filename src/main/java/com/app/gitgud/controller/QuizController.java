package com.app.gitgud.controller;

import com.app.gitgud.model.dto.QuizData;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.service.QuestionService;
import com.app.gitgud.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;
    private final QuestionService questionService;

    public QuizController(
            QuizService quizService,
            QuestionService questionService
    ) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @GetMapping(
            "/api/quizzes/sections/{chapterId}/{sectionId}/{quizId}"
    )
    public QuizData getSectionQuiz(
            @PathVariable String chapterId,
            @PathVariable String sectionId,
            @PathVariable String quizId
    ) throws Exception {

        Quiz quiz = quizService.getSectionQuiz(
                chapterId,
                sectionId,
                quizId
        );

        if (quiz == null) {
            throw new IllegalArgumentException("Quiz not found");
        }

        List<Question> questions =
                questionService.getQuizQuestions(quiz);

        return new QuizData(
                quiz,
                questions
        );
    }
}
