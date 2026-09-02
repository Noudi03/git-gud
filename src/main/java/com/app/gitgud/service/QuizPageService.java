package com.app.gitgud.service;

import com.app.gitgud.mapper.QuizMapper;
import com.app.gitgud.mapper.TrialMapper;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.Trial;
import com.app.gitgud.model.dto.QuizPageDto;
import com.app.gitgud.model.dto.TrialDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizPageService {

    private final QuizService quizService;
    private final TrialService trialService;
    private final QuizMapper quizMapper;
    private final TrialMapper trialMapper;

    public QuizPageService(
            QuizService quizService,
            TrialService trialService,
            QuizMapper quizMapper,
            TrialMapper trialMapper
    ) {

        this.quizService =
                quizService;

        this.trialService =
                trialService;

        this.quizMapper =
                quizMapper;

        this.trialMapper =
                trialMapper;
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

        List<Trial> trials =
                trialService.getQuizTrials(
                        quiz
                );

        List<TrialDto> trialDtos =
                trials.stream()
                        .map(trialMapper::toDto)
                        .toList();

        return new QuizPageDto(
                quizMapper.toDto(quiz),
                trialDtos
        );
    }
}
