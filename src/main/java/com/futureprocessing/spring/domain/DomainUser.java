package com.futureprocessing.spring.domain;

public class DomainUser {
    private String username;

    public DomainUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username;
    }
}
