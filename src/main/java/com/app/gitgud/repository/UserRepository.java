package com.app.gitgud.repository;

import com.app.gitgud.model.User;

public interface UserRepository {

    void createUser(
            User user
    ) throws Exception;

    User getUser(
            String userId
    ) throws Exception;

    void setRating(
            String userId,
            int rating
    ) throws Exception;

    void changeRating(
            String userId,
            int ratingChange
    ) throws Exception;
}
