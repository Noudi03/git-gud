package com.app.gitgud.service;

import com.app.gitgud.mapper.QuestionMapper;
import com.app.gitgud.mapper.QuizMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.dto.QuizPageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizPageService {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;


    public QuizPageService(
            QuizService quizService,
            QuestionService questionService,
            QuizMapper quizMapper,
            QuestionMapper questionMapper
    ) {

        this.quizService =
                quizService;

        this.questionService =
                questionService;

        this.quizMapper =
                quizMapper;

        this.questionMapper =
                questionMapper;
    }


    public QuizPageDto getSectionQuiz(
            String chapterId,
            String sectionId,
            String quizId
    ) throws Exception {

        Quiz quiz =
                quizService.getSectionQuiz(
                        chapterId,
                        sectionId,
                        quizId
                );

        return buildQuizPage(quiz);
    }


    public QuizPageDto getChapterQuiz(
            String chapterId,
            String quizId
    ) throws Exception {

        Quiz quiz =
                quizService.getChapterQuiz(
                        chapterId,
                        quizId
                );

        return buildQuizPage(quiz);
    }


    public QuizPageDto getGlobalQuiz(
            String quizId
    ) throws Exception {

        Quiz quiz =
                quizService.getGlobalQuiz(
                        quizId
                );

        return buildQuizPage(quiz);
    }


    private QuizPageDto buildQuizPage(
            Quiz quiz
    ) throws Exception {

        if (quiz == null) {
            return null;
        }

        List<Question> questions =
                questionService.getQuizQuestions(
                        quiz
                );

        return new QuizPageDto(
                quizMapper.toDto(quiz),
                questions.stream()
                        .map(questionMapper::toDto)
                        .toList()
        );
    }
}
