package com.app.gitgud.model.dto;

import com.app.gitgud.model.TrialDifficulty;
import com.app.gitgud.model.TrialType;

public interface TrialDto {

    String getId();

    TrialType getType();

    TrialDifficulty getDifficulty();

    int getOrder();
}
