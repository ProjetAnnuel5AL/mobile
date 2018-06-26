package com.lechampalamaison.api.model;

public class Login {
    private String loginUser;
    private String passwordUser;

    public Login(String login, String password){
        this.loginUser = login;
        this.passwordUser = password;
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
}
