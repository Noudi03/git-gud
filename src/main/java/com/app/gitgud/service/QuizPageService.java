package com.app.gitgud.service;

import com.app.gitgud.mapper.QuestionMapper;
import com.app.gitgud.mapper.QuizMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.dto.FillInTheBlankQuestionDto;
import com.app.gitgud.model.dto.MultipleChoiceQuestionDto;
import com.app.gitgud.model.dto.QuestionDto;
import com.app.gitgud.model.dto.QuizPageDto;
import com.app.gitgud.model.dto.TrueFalseQuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizPageService {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final MarkdownService markdownService;

    public QuizPageService(
            QuizService quizService,
            QuestionService questionService,
            QuizMapper quizMapper,
            QuestionMapper questionMapper,
            MarkdownService markdownService
    ) {

        this.quizService =
                quizService;

        this.questionService =
                questionService;

        this.quizMapper =
                quizMapper;

        this.questionMapper =
                questionMapper;

        this.markdownService =
                markdownService;
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

        List<QuestionDto> questionDtos =
                questions.stream()
                        .map(questionMapper::toDto)
                        .map(this::convertQuestion)
                        .toList();

        return new QuizPageDto(
                quizMapper.toDto(quiz),
                questionDtos
        );
    }

    private QuestionDto convertQuestion(
            QuestionDto dto
    ) {

        return switch (dto.getType()) {

            case MULTIPLE_CHOICE -> {
                MultipleChoiceQuestionDto q =
                        (MultipleChoiceQuestionDto) dto;
                yield new MultipleChoiceQuestionDto(
                        q.getId(),
                        markdownService.toHtml(q.getText()),
                        convertOptions(q.getOptions()),
                        q.getCorrectAnswer(),
                        q.getDifficulty(),
                        convertHints(q.getHints()),
                        q.getOrder()
                );
            }

            case TRUE_FALSE -> {
                TrueFalseQuestionDto q =
                        (TrueFalseQuestionDto) dto;
                yield new TrueFalseQuestionDto(
                        q.getId(),
                        markdownService.toHtml(q.getText()),
                        q.isCorrectAnswer(),
                        q.getDifficulty(),
                        convertHints(q.getHints()),
                        q.getOrder()
                );
            }

            case FILL_IN_THE_BLANK -> {
                FillInTheBlankQuestionDto q =
                        (FillInTheBlankQuestionDto) dto;
                yield new FillInTheBlankQuestionDto(
                        q.getId(),
                        markdownService.toHtml(q.getText()),
                        q.getCorrectAnswer(),
                        q.getDifficulty(),
                        convertHints(q.getHints()),
                        q.getOrder()
                );
            }
        };
    }

    private Map<String, String> convertOptions(
            Map<String, String> options
    ) {

        if (options == null) {
            return Map.of();
        }

        return options.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> markdownService.toHtml(e.getValue())
                        )
                );
    }

    private List<String> convertHints(
            List<String> hints
    ) {

        if (hints == null) {
            return List.of();
        }

        return hints.stream()
                .map(markdownService::toHtml).toList();
    }
}