package com.app.gitgud.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class GuestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        Object uid = request.getSession().getAttribute("uid");

        if (uid != null) {
            response.sendRedirect("/home");
            return false;
        }

        return true;
    }
}
