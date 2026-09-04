package com.app.gitgud.model.dto;

import com.app.gitgud.model.QuestionDifficulty;
import com.app.gitgud.model.QuestionType;

public interface QuestionDto {

    String getId();

    QuestionType getType();

    QuestionDifficulty getDifficulty();
}
