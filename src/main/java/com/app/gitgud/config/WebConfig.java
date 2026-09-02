package com.app.gitgud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final GuestInterceptor guestInterceptor;


    public WebConfig(
            AuthInterceptor authInterceptor,
            GuestInterceptor guestInterceptor
    ) {

        this.authInterceptor =
                authInterceptor;

        this.guestInterceptor =
                guestInterceptor;
    }


    @Override
    public void addInterceptors(
            InterceptorRegistry registry
    ) {

        registry.addInterceptor(
                authInterceptor
        ).addPathPatterns(
                "/home",
                "/tutorials/**",
                "/statistics"
        );


        registry.addInterceptor(
                guestInterceptor
        ).addPathPatterns(
                "/login",
                "/register"
        );
    }
}
