package com.app.gitgud.service;

import com.app.gitgud.mapper.QuestionAttemptMapper;
import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.FillInTheBlankQuestion;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.MultipleChoiceQuestion;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.QuestionAttempt;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizAttempt;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.model.TrueFalseQuestion;
import com.app.gitgud.model.dto.QuestionAttemptRequestDto;
import com.app.gitgud.repository.FirestoreQuestionAttemptRepository;
import com.app.gitgud.repository.FirestoreQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionAttemptService {

    private final FirestoreQuestionAttemptRepository questionAttemptRepository;
    private final QuestionAttemptMapper questionAttemptMapper;
    private final QuizAttemptService quizAttemptService;
    private final QuizService quizService;
    private final FirestoreQuestionRepository questionRepository;

    public QuestionAttemptService(
            FirestoreQuestionAttemptRepository questionAttemptRepository,
            QuestionAttemptMapper questionAttemptMapper,
            QuizAttemptService quizAttemptService,
            QuizService quizService,
            FirestoreQuestionRepository questionRepository
    ) {
        this.questionAttemptRepository =
                questionAttemptRepository;

        this.questionAttemptMapper =
                questionAttemptMapper;

        this.quizAttemptService =
                quizAttemptService;

        this.quizService =
                quizService;

        this.questionRepository =
                questionRepository;
    }

    public void createQuestionAttempt(
            String userId,
            String attemptId,
            QuestionAttemptRequestDto request
    ) throws Exception {

        if (request.getQuestionId() == null) {
            throw new IllegalArgumentException(
                    "Question ID is required"
            );
        }

        QuizAttempt quizAttempt =
                quizAttemptService.getAttempt(
                        userId,
                        attemptId
                );

        Quiz quiz =
                getQuiz(
                        quizAttempt
                );

        Question question =
                questionRepository.findQuestion(
                        quiz,
                        request.getQuestionId()
                );

        if (question == null) {
            throw new IllegalArgumentException(
                    "Question not found"
            );
        }

        boolean correct =
                isCorrect(
                        question,
                        request.getSelectedAnswer()
                );

        String questionAttemptId =
                UUID.randomUUID().toString();

        QuestionAttempt questionAttempt =
                questionAttemptMapper.toModel(
                        questionAttemptId,
                        request,
                        correct
                );

        questionAttemptRepository.createQuestionAttempt(
                userId,
                attemptId,
                questionAttempt
        );
    }

    public List<QuestionAttempt> getQuestionAttempts(
            String userId,
            String attemptId
    ) throws Exception {
        return questionAttemptRepository
                .findQuestionAttempts(
                        userId,
                        attemptId
                );
    }

    public QuestionAttempt getQuestionAttempt(
            String userId,
            String attemptId,
            String questionId
    ) throws Exception {
        return questionAttemptRepository
                .findQuestionAttempt(
                        userId,
                        attemptId,
                        questionId
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Question attempt not found"
                        )
                );
    }

    private boolean isCorrect(
            Question question,
            Object selectedAnswer
    ) {
        if (selectedAnswer == null) {
            return false;
        }

        if (question instanceof MultipleChoiceQuestion multipleChoice) {
            return selectedAnswer instanceof String
                    && selectedAnswer.equals(
                    multipleChoice.getCorrectAnswer()
            );
        }

        if (question instanceof FillInTheBlankQuestion fillInTheBlank) {
            return normalizeAnswer(
                    selectedAnswer
            ).equals(
                    normalizeAnswer(
                            fillInTheBlank.getCorrectAnswer()
                    )
            );
        }

        if (question instanceof TrueFalseQuestion trueFalse) {
            if (!(selectedAnswer instanceof Boolean)) {
                return false;
            }

            return (Boolean) selectedAnswer
                    == trueFalse.isCorrectAnswer();
        }

        return false;
    }

    private String normalizeAnswer(
            Object answer
    ) {
        return String.valueOf(answer)
                .trim()
                .toLowerCase();
    }

    private Quiz getQuiz(
            QuizAttempt attempt
    ) throws Exception {

        QuizContext context =
                attempt.getContext();

        if (context instanceof SectionQuizContext sectionContext) {
            return quizService.getSectionQuiz(
                    sectionContext.getChapterId(),
                    sectionContext.getSectionId(),
                    attempt.getQuizId()
            );
        }

        if (context instanceof ChapterQuizContext chapterContext) {
            return quizService.getChapterQuiz(
                    chapterContext.getChapterId(),
                    attempt.getQuizId()
            );
        }

        if (context instanceof GlobalQuizContext) {
            return quizService.getGlobalQuiz(
                    attempt.getQuizId()
            );
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }
}
