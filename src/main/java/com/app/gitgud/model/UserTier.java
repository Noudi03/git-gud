package com.app.gitgud.model;

public enum UserTier {

    NOVICE("novice"),
    EXPERIENCED("experienced");

    private final String value;

    UserTier(
            String value
    ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserTier fromValue(
            String value
    ) {

        for (UserTier tier : values()) {

            if (tier.value.equalsIgnoreCase(value)) {
                return tier;
            }
        }

        throw new IllegalArgumentException(
                "Unknown user tier: " + value
        );
    }
}
