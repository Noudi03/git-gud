package com.app.gitgud.model;

public interface Trial {

    String getId();

    TrialType getType();

    TrialDifficulty getDifficulty();

    int getOrder();
}
