package com.lechampalamaison.api.model;

public class Signup {

    private String loginUser;
    private String emailUser;
    private String passwordUser;

    public Signup(String loginUser, String mailUser, String passwordUser) {
        this.loginUser = loginUser;
        this.emailUser = mailUser;
        this.passwordUser = passwordUser;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getMailUser() {
        return emailUser;
    }

    public void setMailUser(String mailUser) {
        this.emailUser = mailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
}
