package com.app.gitgud.model;

public enum QuizAttemptStatus {

    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private final String value;


    QuizAttemptStatus(
            String value
    ) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }


    public static QuizAttemptStatus fromValue(
            String value
    ) {

        for (QuizAttemptStatus status : values()) {

            if (
                    status.value.equalsIgnoreCase(
                            value
                    )
            ) {
                return status;
            }
        }

        throw new IllegalArgumentException(
                "Unknown quiz attempt status: "
                        + value
        );
    }
}
