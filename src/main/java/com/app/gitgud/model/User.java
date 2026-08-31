package com.app.gitgud.model;

public class User {

    private final String id;
    private final String email;
    private final String username;


    public User(
            String id,
            String email,
            String username
    ) {

        this.id = id;
        this.email = email;
        this.username = username;
    }


    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
