package com.app.gitgud.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private static final String UID_ATTRIBUTE = "uid";
    private static final String EMAIL_ATTRIBUTE = "email";

    private final HttpSession session;


    public CurrentUserService(
            HttpSession session
    ) {
        this.session = session;
    }


    public String getUserId() {

        return (String)
                session.getAttribute(
                        UID_ATTRIBUTE
                );
    }


    public String getEmail() {

        return (String)
                session.getAttribute(
                        EMAIL_ATTRIBUTE
                );
    }


    public boolean isAuthenticated() {

        return getUserId() != null;
    }
}
