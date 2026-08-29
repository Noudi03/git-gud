package com.app.gitgud.model.dto;

import com.app.gitgud.model.Question;
import com.app.gitgud.model.Quiz;

import java.util.List;

public record QuizData(
        Quiz quiz,
        List<Question> questions
) {
}
