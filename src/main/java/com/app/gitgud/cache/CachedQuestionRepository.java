package com.app.gitgud.cache;

import com.app.gitgud.mapper.FirestoreQuestionMapper;
import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.repository.FirestoreQuestionRepository;
import com.google.cloud.firestore.Firestore;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class CachedQuestionRepository
        extends FirestoreQuestionRepository {

    public CachedQuestionRepository(
            Firestore db,
            FirestoreQuestionMapper mapper
    ) {
        super(db, mapper);
    }

    @Override
    @Cacheable(
            value = "questionLists",
            key = "@cacheKeyBuilder.forQuiz(#quiz)"
    )
    public List<Question> findQuestions(
            Quiz quiz
    ) throws Exception {

        return super.findQuestions(quiz);
    }

    @Override
    @Cacheable(
            value = "questions",
            key = "@cacheKeyBuilder.forQuiz(#quiz) + ':' + #questionId"
    )
    public Question findQuestion(
            Quiz quiz,
            String questionId
    ) throws Exception {

        return super.findQuestion(
                quiz,
                questionId
        );
    }
}