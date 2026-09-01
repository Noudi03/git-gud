package com.app.gitgud.service;

import com.app.gitgud.mapper.FirestoreQuestionMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.repository.FirestoreQuizRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final FirestoreQuizRepository firestoreQuizRepository;
    private final FirestoreQuestionMapper firestoreQuestionMapper;


    public QuestionService(
            FirestoreQuizRepository firestoreQuizRepository,
            FirestoreQuestionMapper firestoreQuestionMapper
    ) {

        this.firestoreQuizRepository =
                firestoreQuizRepository;

        this.firestoreQuestionMapper =
                firestoreQuestionMapper;
    }


    public List<Question> getQuizQuestions(
            Quiz quiz
    ) throws Exception {

        QuerySnapshot snapshot =
                firestoreQuizRepository
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
                firestoreQuizRepository
                        .getQuestionsCollection(
                                quiz.getContext(),
                                quiz.getId()
                        )
                        .document(questionId)
                        .get()
                        .get();

        return firestoreQuestionMapper.toQuestion(
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
                    firestoreQuestionMapper.toQuestion(
                            document
                    );

            if (question != null) {
                questions.add(question);
            }
        }

        return questions;
    }
}
