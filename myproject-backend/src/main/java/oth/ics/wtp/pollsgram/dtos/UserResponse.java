package oth.ics.wtp.pollsgram.dtos;

import java.util.List;

public class UserResponse {

    private Long id;
    private String username;
    private List<String> roles;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}