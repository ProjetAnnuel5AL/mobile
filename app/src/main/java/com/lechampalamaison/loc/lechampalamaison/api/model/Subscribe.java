package com.lechampalamaison.loc.lechampalamaison.api.model;

public class Subscribe {

    private int idGroup;
    private String token;

    public Subscribe(int idGroup, String token) {
        this.idGroup = idGroup;
        this.token = token;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
