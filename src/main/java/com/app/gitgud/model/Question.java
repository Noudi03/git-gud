package com.app.gitgud.model;

public interface Question {

    String getId();

    QuestionType getType();

    QuestionDifficulty getDifficulty();

    int getOrder();
}
