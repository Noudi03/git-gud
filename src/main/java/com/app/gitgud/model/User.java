package com.app.gitgud.model;

import java.util.Date;

public class User {
    private String id;
    private String name;
    private String username;
    private Date since;

    public User() {}

    public User(String id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.since = new Date();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }
}
