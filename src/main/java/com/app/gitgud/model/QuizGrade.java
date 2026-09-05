package com.app.gitgud.model;

public enum QuizGrade {

    FAILURE("failure"),
    PASSING("passing"),
    EXCELLENT("excellent"),
    PERFECT("perfect");

    private final String value;

    QuizGrade(
            String value
    ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuizGrade fromValue(
            String value
    ) {

        for (QuizGrade grade : values()) {

            if (grade.value.equalsIgnoreCase(value)) {
                return grade;
            }
        }

        throw new IllegalArgumentException(
                "Unknown quiz grade: " + value
        );
    }
}
