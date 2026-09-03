package com.app.gitgud.model;

public enum QuestionType {

    MULTIPLE_CHOICE("multipleChoice"),
    FILL_IN_THE_BLANK("fillInTheBlank"),
    TRUE_FALSE("trueFalse");

    private final String value;

    QuestionType(
            String value
    ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuestionType fromValue(
            String value
    ) {

        for (QuestionType type : values()) {

            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Unknown question type: " + value
        );
    }
}
