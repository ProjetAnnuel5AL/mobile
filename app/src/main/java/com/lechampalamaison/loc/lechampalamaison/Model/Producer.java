package com.lechampalamaison.loc.lechampalamaison.Model;

public class Producer {

    private int idProducer;
    private String lastNameProducer;
    private String firstNameProducer;
    private String addressProducer;
    private String roleProducer;
    private String avatarProducer;


    public Producer(int idProducer, String lastNameProducer, String firstNameProducer, String addressProducer, String roleProducer, String avatarProducer) {
        this.idProducer = idProducer;
        this.lastNameProducer = lastNameProducer;
        this.firstNameProducer = firstNameProducer;
        this.addressProducer = addressProducer;
        this.roleProducer = roleProducer;
        this.avatarProducer = avatarProducer;
    }

    public String getLastNameProducer() {
        return lastNameProducer;
    }

    public void setLastNameProducer(String lastNameProducer) {
        this.lastNameProducer = lastNameProducer;
    }

    public String getFirstNameProducer() {
        return firstNameProducer;
    }

    public void setFirstNameProducer(String firstNameProducer) {
        this.firstNameProducer = firstNameProducer;
    }

    public String getAddressProducer() {
        return addressProducer;
    }

    public void setAddressProducer(String addressProducer) {
        this.addressProducer = addressProducer;
    }

    public String getRoleProducer() {
        return roleProducer;
    }

    public void setRoleProducer(String roleProducer) {
        this.roleProducer = roleProducer;
    }

    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public String getAvatarProducer() {
        return avatarProducer;
    }

    public void setAvatarProducer(String avatarProducer) {
        this.avatarProducer = avatarProducer;
    }
}
