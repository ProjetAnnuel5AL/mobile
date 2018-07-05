package com.lechampalamaison.api.model;

public class User {
    private String loginUser;
    private String token;
    private String passwordUser;
    private String emailUser;
    private String lastNameUser;
    private String firstNameUser;
    private String sexUser;
    private String addressUser;
    private String cityUser;
    private String cpUser;

    public User(){}

    public User(String loginUser, String token, String passwordUser, String emailUser, String lastNameUser, String firstNameUser, String sexUser, String addressUser, String cityUser, String cpUser) {
        this.loginUser = loginUser;
        this.token = token;
        this.passwordUser = passwordUser;
        this.emailUser = emailUser;
        this.lastNameUser = lastNameUser;
        this.firstNameUser = firstNameUser;
        this.sexUser = sexUser;
        this.addressUser = addressUser;
        this.cityUser = cityUser;
        this.cpUser = cpUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getFirstNameUser() {
        return firstNameUser;
    }

    public void setFirstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
    }

    public String getSexUser() {
        return sexUser;
    }

    public void setSexUser(String sexUser) {
        this.sexUser = sexUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getCityUser() {
        return cityUser;
    }

    public void setCityUser(String cityUser) {
        this.cityUser = cityUser;
    }

    public String getCpUser() {
        return cpUser;
    }

    public void setCpUser(String cpUser) {
        this.cpUser = cpUser;
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
