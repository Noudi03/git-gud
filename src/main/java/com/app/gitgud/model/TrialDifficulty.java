package com.app.gitgud.model;

public enum TrialDifficulty {

    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");

    private final String value;

    TrialDifficulty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TrialDifficulty fromValue(
            String value
    ) {

        for (TrialDifficulty difficulty : values()) {

            if (difficulty.value.equalsIgnoreCase(value)) {
                return difficulty;
            }
        }

        throw new IllegalArgumentException(
                "Unknown trial difficulty: " + value
        );
    }
}
