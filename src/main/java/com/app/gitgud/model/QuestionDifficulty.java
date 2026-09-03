package com.app.gitgud.model;

public enum QuestionDifficulty {

    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");

    private final String value;

    QuestionDifficulty(
            String value
    ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuestionDifficulty fromValue(
            String value
    ) {

        for (QuestionDifficulty difficulty : values()) {

            if (difficulty.value.equalsIgnoreCase(value)) {
                return difficulty;
            }
        }

        throw new IllegalArgumentException(
                "Unknown question difficulty: " + value
        );
    }
}
