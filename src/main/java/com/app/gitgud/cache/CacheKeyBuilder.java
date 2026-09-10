package com.app.gitgud.cache;

import com.app.gitgud.model.ChapterQuizContext;
import com.app.gitgud.model.GlobalQuizContext;
import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.QuizContext;
import com.app.gitgud.model.SectionQuizContext;
import org.springframework.stereotype.Component;

// Builds cache keys for entities whose identity depends on a
// polymorphic QuizContext (Quiz, Question), since QuizContext
// implementations do not define equals()/hashCode().
@Component("cacheKeyBuilder")
public class CacheKeyBuilder {

    public String forQuizContext(
            QuizContext context
    ) {

        if (
                context instanceof SectionQuizContext
                        sectionContext
        ) {

            return "section:"
                    + sectionContext.getChapterId()
                    + ":"
                    + sectionContext.getSectionId();
        }

        if (
                context instanceof ChapterQuizContext
                        chapterContext
        ) {

            return "chapter:"
                    + chapterContext.getChapterId();
        }

        if (
                context instanceof GlobalQuizContext
        ) {

            return "global";
        }

        throw new IllegalArgumentException(
                "Unsupported quiz context: "
                        + context.getClass().getName()
        );
    }

    public String forQuiz(
            Quiz quiz
    ) {

        return forQuizContext(quiz.getContext())
                + ":"
                + quiz.getId();
    }
}