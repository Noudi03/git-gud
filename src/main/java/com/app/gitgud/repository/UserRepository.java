package com.app.gitgud.repository;

import com.app.gitgud.model.User;

import java.util.List;

public interface UserRepository {

    void createUser(
            User user
    ) throws Exception;

    User getUser(
            String userId
    ) throws Exception;

    List<User> getUsers()
            throws Exception;

    void setRating(
            String userId,
            int rating
    ) throws Exception;
}
