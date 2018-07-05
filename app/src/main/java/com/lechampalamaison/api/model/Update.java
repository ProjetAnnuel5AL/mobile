package com.lechampalamaison.api.model;

public class Update {
    private String loginUser;
    private String passwordUser;
    private String token;
    private String emailUser;

    public Update(String loginUser, String token, String passwordUser, String emailUser) {
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
        this.token = token;
        this.emailUser= emailUser;
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

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
