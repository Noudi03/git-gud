package com.app.gitgud.service;

import com.app.gitgud.mapper.QuestionFirestoreMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.repository.QuizFirestoreRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuizFirestoreRepository quizFirestoreRepository;
    private final QuestionFirestoreMapper questionFirestoreMapper;


    public QuestionService(
            QuizFirestoreRepository quizFirestoreRepository,
            QuestionFirestoreMapper questionFirestoreMapper
    ) {

        this.quizFirestoreRepository =
                quizFirestoreRepository;

        this.questionFirestoreMapper =
                questionFirestoreMapper;
    }


    public List<Question> getQuizQuestions(
            Quiz quiz
    ) throws Exception {

        QuerySnapshot snapshot =
                quizFirestoreRepository
                        .getQuestionsCollection(
                                quiz.getContext(),
                                quiz.getId()
                        )
                        .orderBy("order")
                        .get()
                        .get();

        return convertQuestions(snapshot);
    }


    public Question getQuizQuestion(
            Quiz quiz,
            String questionId
    ) throws Exception {

        DocumentSnapshot document =
                quizFirestoreRepository
                        .getQuestionsCollection(
                                quiz.getContext(),
                                quiz.getId()
                        )
                        .document(questionId)
                        .get()
                        .get();

        return questionFirestoreMapper.toQuestion(
                document
        );
    }


    private List<Question> convertQuestions(
            QuerySnapshot snapshot
    ) {

        List<Question> questions =
                new ArrayList<>();

        for (
                DocumentSnapshot document :
                snapshot.getDocuments()
        ) {

            Question question =
                    questionFirestoreMapper.toQuestion(
                            document
                    );

            if (question != null) {
                questions.add(question);
            }
        }

        return questions;
    }
}
