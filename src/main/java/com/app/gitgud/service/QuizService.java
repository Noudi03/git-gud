package com.app.gitgud.service;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import com.app.gitgud.repository.FirestoreQuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final FirestoreQuizRepository quizRepository;


    public QuizService(
            FirestoreQuizRepository quizRepository
    ) {

        this.quizRepository =
                quizRepository;
    }


    public List<Quiz> getSectionQuizzes(
            String chapterId,
            String sectionId
    ) throws Exception {

        QuizContext context =
                new SectionQuizContext(
                        chapterId,
                        sectionId
                );

        return quizRepository.findQuizzes(
                context
        );
    }


    public Quiz getSectionQuiz(
            String chapterId,
            String sectionId,
            String quizId
    ) throws Exception {

        QuizContext context =
                new SectionQuizContext(
                        chapterId,
                        sectionId
                );

        return quizRepository.findQuiz(
                context,
                quizId
        );
    }


    public List<Quiz> getChapterQuizzes(
            String chapterId
    ) throws Exception {

        QuizContext context =
                new ChapterQuizContext(
                        chapterId
                );

        return quizRepository.findQuizzes(
                context
        );
    }


    public Quiz getChapterQuiz(
            String chapterId,
            String quizId
    ) throws Exception {

        QuizContext context =
                new ChapterQuizContext(
                        chapterId
                );

        return quizRepository.findQuiz(
                context,
                quizId
        );
    }


    public List<Quiz> getGlobalQuizzes()
            throws Exception {

        QuizContext context =
                new GlobalQuizContext();

        return quizRepository.findQuizzes(
                context
        );
    }


    public Quiz getGlobalQuiz(
            String quizId
    ) throws Exception {

        QuizContext context =
                new GlobalQuizContext();

        return quizRepository.findQuiz(
                context,
                quizId
        );
    }
}
