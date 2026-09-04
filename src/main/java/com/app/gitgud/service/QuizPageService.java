package com.app.gitgud.service;

import com.app.gitgud.mapper.QuestionMapper;
import com.app.gitgud.mapper.QuizMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.User;
import com.app.gitgud.model.dto.QuestionDto;
import com.app.gitgud.model.dto.QuizPageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizPageService {

    private final QuizService quizService;
    private final QuestionSelectionService questionSelectionService;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;

    public QuizPageService(
            QuizService quizService,
            QuestionSelectionService questionSelectionService,
            QuizMapper quizMapper,
            QuestionMapper questionMapper
    ) {

        this.quizService =
                quizService;

        this.questionSelectionService =
                questionSelectionService;

        this.quizMapper =
                quizMapper;

        this.questionMapper =
                questionMapper;
    }

    public QuizPageDto getSectionQuiz(
            String chapterId,
            String sectionId,
            String quizId,
            User user
    ) throws Exception {

        Quiz quiz =
                quizService.getSectionQuiz(
                        chapterId,
                        sectionId,
                        quizId
                );

        return buildQuizPage(
                quiz,
                user
        );
    }

    public QuizPageDto getChapterQuiz(
            String chapterId,
            String quizId,
            User user
    ) throws Exception {

        Quiz quiz =
                quizService.getChapterQuiz(
                        chapterId,
                        quizId
                );

        return buildQuizPage(
                quiz,
                user
        );
    }

    public QuizPageDto getGlobalQuiz(
            String quizId,
            User user
    ) throws Exception {

        Quiz quiz =
                quizService.getGlobalQuiz(
                        quizId
                );

        return buildQuizPage(
                quiz,
                user
        );
    }

    private QuizPageDto buildQuizPage(
            Quiz quiz,
            User user
    ) throws Exception {

        if (quiz == null) {
            return null;
        }

        if (user == null) {
            throw new IllegalArgumentException(
                    "User not found"
            );
        }

        List<Question> questions =
                questionSelectionService.selectQuestions(
                        user,
                        quiz
                );

        List<QuestionDto> questionDtos =
                questions.stream()
                        .map(questionMapper::toDto)
                        .toList();

        return new QuizPageDto(
                quizMapper.toDto(quiz),
                questionDtos
        );
    }
}
