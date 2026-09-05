package com.app.gitgud.service.selection;

import com.app.gitgud.model.Question;
import com.app.gitgud.model.User;

import java.util.List;

public interface QuestionSelectionStrategy {

    List<Question> selectQuestions(
            User user,
            List<Question> questions,
            int questionCount
    );
}
