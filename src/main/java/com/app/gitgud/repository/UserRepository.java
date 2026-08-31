package com.app.gitgud.repository;

import com.app.gitgud.model.User;

public interface UserRepository {

    void createUser(
            User user
    ) throws Exception;

    User getUser(
            String userId
    ) throws Exception;
}
