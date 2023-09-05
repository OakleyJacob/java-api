package com.revature.p1.dtos.requests;

public class NewDeactivationRequest {
    private String username;

    public NewDeactivationRequest() {
    }

    public NewDeactivationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
