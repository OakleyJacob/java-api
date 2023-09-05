package com.revature.p1.dtos.requests;

public class NewPasswordChange {
    private String username;
    private String password1;
    private String password2;

    public NewPasswordChange() {
        super();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "NewPasswordChange{" +
                "username='" + username + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public NewPasswordChange(String username, String password1, String password2) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
    }
}