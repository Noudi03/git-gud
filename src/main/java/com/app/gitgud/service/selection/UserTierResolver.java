package com.app.gitgud.service.selection;

import com.app.gitgud.model.User;
import com.app.gitgud.model.UserTier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserTierResolver {

    private final int experiencedThreshold;

    public UserTierResolver(
            @Value("${gitgud.rating.experienced-threshold}")
            int experiencedThreshold
    ) {

        this.experiencedThreshold =
                experiencedThreshold;
    }

    public UserTier resolve(
            User user
    ) {

        if (
                user.getRating()
                        >= experiencedThreshold
        ) {

            return UserTier.EXPERIENCED;
        }

        return UserTier.NOVICE;
    }
}
