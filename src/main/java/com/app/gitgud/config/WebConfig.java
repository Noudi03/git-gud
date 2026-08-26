package com.app.gitgud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Protected pages
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns(
                        "/home",
                        "/tutorials/**",
                        "/statistics"
                );

        // Guest-only pages
        registry.addInterceptor(new GuestInterceptor())
                .addPathPatterns(
                        "/login",
                        "/register"
                );
    }
}
