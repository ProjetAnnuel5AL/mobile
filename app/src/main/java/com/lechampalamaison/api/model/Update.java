package com.lechampalamaison.api.model;

public class Update {
    private String loginUser;
    private String passwordUser;
    private String token;

    public Update(String loginUser, String passwordUser, String token) {
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
        this.token = token;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
