package com.app.gitgud.config;

import com.app.gitgud.service.CurrentUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor
        implements HandlerInterceptor {

    private final CurrentUserService currentUserService;


    public AuthInterceptor(
            CurrentUserService currentUserService
    ) {
        this.currentUserService =
                currentUserService;
    }


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        if (!currentUserService.isAuthenticated()) {

            response.sendRedirect("/login");

            return false;
        }

        return true;
    }
}
