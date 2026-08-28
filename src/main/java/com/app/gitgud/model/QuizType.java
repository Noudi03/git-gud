package com.app.gitgud.model;

public enum QuizType {
    INTRODUCTORY("introductory"),
    PRACTICE("practice"),
    FINAL("final"),
    MASTER("master"),
    REVIEW("review");

    private final String value;

    QuizType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
