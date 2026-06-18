package oth.ics.wtp.pollsgram.dtos;

import java.util.List;

public class AuthResponse {

    private String username;
    private List<String> roles;
    private boolean loggedIn;

    public AuthResponse() {
    }

    public AuthResponse(String username, List<String> roles, boolean loggedIn) {
        this.username = username;
        this.roles = roles;
        this.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}