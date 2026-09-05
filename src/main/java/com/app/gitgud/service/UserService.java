package com.app.gitgud.service;

import com.app.gitgud.model.User;
import com.app.gitgud.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository =
                userRepository;
    }

    public void createUser(
            User user
    ) throws Exception {

        userRepository.createUser(
                user
        );
    }

    public User getUser(
            String userId
    ) throws Exception {

        return userRepository.getUser(
                userId
        );
    }

    public void setRating(
            String userId,
            int rating
    ) throws Exception {

        userRepository.setRating(
                userId,
                rating
        );
    }
}
