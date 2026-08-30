package com.app.gitgud.mapper;

import com.app.gitgud.model.Quiz;
import com.app.gitgud.model.dto.QuizDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizMapper {

    public QuizDto toDto(
            Quiz quiz
    ) {

        if (quiz == null) {
            return null;
        }

        return new QuizDto(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getType(),
                quiz.getOrder()
        );
    }


    public List<QuizDto> toDtoList(
            List<Quiz> quizzes
    ) {

        return quizzes.stream()
                .map(this::toDto)
                .toList();
    }
}
