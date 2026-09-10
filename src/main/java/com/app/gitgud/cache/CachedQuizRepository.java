package com.app.gitgud.cache;

import com.app.gitgud.mapper.FirestoreQuizMapper;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.repository.FirestoreQuizRepository;
import com.google.cloud.firestore.Firestore;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class CachedQuizRepository
        extends FirestoreQuizRepository {

    public CachedQuizRepository(
            Firestore db,
            FirestoreQuizMapper mapper
    ) {
        super(db, mapper);
    }

    @Override
    @Cacheable(
            value = "quizLists",
            key = "@cacheKeyBuilder.forQuizContext(#context)"
    )
    public List<Quiz> findQuizzes(
            QuizContext context
    ) throws Exception {

        return super.findQuizzes(context);
    }

    @Override
    @Cacheable(
            value = "quizzes",
            key = "@cacheKeyBuilder.forQuizContext(#context) + ':' + #quizId"
    )
    public Quiz findQuiz(
            QuizContext context,
            String quizId
    ) throws Exception {

        return super.findQuiz(
                context,
                quizId
        );
    }
}