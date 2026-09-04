package com.app.gitgud.model;

public class User {

    private final String id;
    private final String email;
    private final String username;
    private int progression;
    private int rating;

    public User(
            String id,
            String email,
            String username
    ) {

        this(
                id,
                email,
                username,
                0,
                1000
        );
    }

    public User(
            String id,
            String email,
            String username,
            int progression,
            int rating
    ) {

        this.id = id;
        this.email = email;
        this.username = username;
        this.progression = progression;
        this.rating = rating;
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

    public int getProgression() {
        return progression;
    }

    public void setProgression(
            int progression
    ) {

        this.progression = progression;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(
            int rating
    ) {

        this.rating = rating;
    }
}
