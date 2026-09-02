package com.app.gitgud.model;

public enum TrialType {

    QUESTION("question"),
    FILL_IN_THE_BLANK("fillInTheBlank");

    private final String value;

    TrialType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TrialType fromValue(
            String value
    ) {

        for (TrialType type : values()) {

            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Unknown trial type: " + value
        );
    }
}
