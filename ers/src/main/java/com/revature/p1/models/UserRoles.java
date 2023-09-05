package com.revature.p1.models;

public class UserRoles {
    private String role_id;
    private Role role;


    public UserRoles() {
        super();
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserRoles(String role_id, Role role) {
        this.role_id = role_id;
        this.role = role;
    }


    @Override
    public String toString() {
        return "UserRoles{" +
                "role_id='" + role_id + '\'' +
                ", role=" + role +
                '}';
    }
}
