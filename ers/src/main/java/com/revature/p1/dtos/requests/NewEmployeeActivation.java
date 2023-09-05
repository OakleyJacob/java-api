package com.revature.p1.dtos.requests;

public class NewEmployeeActivation {
    private String role_id;
    private String username;

    public NewEmployeeActivation() {
        super();
    }

    public NewEmployeeActivation(String role_id, String username) {
        this.role_id = role_id;
        this.username = username;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "NewEmployeeActivation{" +
                "role_id='" + role_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

