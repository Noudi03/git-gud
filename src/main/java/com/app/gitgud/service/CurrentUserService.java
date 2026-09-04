package com.app.gitgud.service;

import com.app.gitgud.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private static final String UID_ATTRIBUTE = "uid";

    private final HttpSession session;
    private final UserService userService;


    public CurrentUserService(
            HttpSession session,
            UserService userService
    ) {

        this.session =
                session;

        this.userService =
                userService;
    }


    public void login(
            String userId
    ) {

        session.setAttribute(
                UID_ATTRIBUTE,
                userId
        );
    }


    public void logout() {

        session.invalidate();
    }


    public String getUserId() {

        return (String)
                session.getAttribute(
                        UID_ATTRIBUTE
                );
    }


    public User getUser() throws Exception {

        String userId =
                getUserId();

        if (userId == null) {
            return null;
        }

        return userService.getUser(
                userId
        );
    }


    public boolean isAuthenticated() {

        return getUserId() != null;
    }
}
