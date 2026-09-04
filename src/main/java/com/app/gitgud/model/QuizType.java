package com.app.gitgud.model;

public enum QuizType {

    INTRODUCTORY("introductory"),
    REVIEW("review");

    private final String value;

    QuizType(
            String value
    ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuizType fromValue(
            String value
    ) {

        for (QuizType type : values()) {

            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Unknown quiz type: " + value
        );
    }
}
