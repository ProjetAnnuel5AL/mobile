package com.lechampalamaison.loc.lechampalamaison.Model;

public class LigneOrder {


    private int idLigneOrder;
    private int idItemLigneOrder;
    private String unitLigneOrder;
    private String categoryLigneOrder;
    private String productLigneOrder;
    private String titleLigneOrder;
    private Double quantiteLigneOrder;
    private Double prixUnitaireLigneOrder;
    private String statusPaypalTransact;
    private int idProducer;
    private String emailProducer;
    private String lastNameProducer;
    private String firstNameProducer;
    private String loginUser;
    private Double shippingCostLigneOrder;
    private String deliveryTimeLigneOrder;
    private int idDeliveryLigneOrder;
    private String nameDelivery;

    public LigneOrder(int idLigneOrder, int idItemLigneOrder, String unitLigneOrder, String categoryLigneOrder, String productLigneOrder, String titleLigneOrder, Double quantiteLigneOrder, Double prixUnitaireLigneOrder, String statusPaypalTransact, int idProducer, String emailProducer, String lastNameProducer, String firstNameProducer, String loginUser, Double shippingCostLigneOrder, String deliveryTimeLigneOrder, int idDeliveryLigneOrder, String nameDelivery) {
        this.idLigneOrder = idLigneOrder;
        this.idItemLigneOrder = idItemLigneOrder;
        this.unitLigneOrder = unitLigneOrder;
        this.categoryLigneOrder = categoryLigneOrder;
        this.productLigneOrder = productLigneOrder;
        this.titleLigneOrder = titleLigneOrder;
        this.quantiteLigneOrder = quantiteLigneOrder;
        this.prixUnitaireLigneOrder = prixUnitaireLigneOrder;
        this.statusPaypalTransact = statusPaypalTransact;
        this.idProducer = idProducer;
        this.emailProducer = emailProducer;
        this.lastNameProducer = lastNameProducer;
        this.firstNameProducer = firstNameProducer;
        this.loginUser = loginUser;
        this.shippingCostLigneOrder = shippingCostLigneOrder;
        this.deliveryTimeLigneOrder = deliveryTimeLigneOrder;
        this.idDeliveryLigneOrder = idDeliveryLigneOrder;
        this.nameDelivery = nameDelivery;
    }

    public int getIdLigneOrder() {
        return idLigneOrder;
    }

    public void setIdLigneOrder(int idLigneOrder) {
        this.idLigneOrder = idLigneOrder;
    }

    public int getIdItemLigneOrder() {
        return idItemLigneOrder;
    }

    public void setIdItemLigneOrder(int idItemLigneOrder) {
        this.idItemLigneOrder = idItemLigneOrder;
    }

    public String getUnitLigneOrder() {
        return unitLigneOrder;
    }

    public void setUnitLigneOrder(String unitLigneOrder) {
        this.unitLigneOrder = unitLigneOrder;
    }

    public String getCategoryLigneOrder() {
        return categoryLigneOrder;
    }

    public void setCategoryLigneOrder(String categoryLigneOrder) {
        this.categoryLigneOrder = categoryLigneOrder;
    }

    public String getProductLigneOrder() {
        return productLigneOrder;
    }

    public void setProductLigneOrder(String productLigneOrder) {
        this.productLigneOrder = productLigneOrder;
    }

    public String getTitleLigneOrder() {
        return titleLigneOrder;
    }

    public void setTitleLigneOrder(String titleLigneOrder) {
        this.titleLigneOrder = titleLigneOrder;
    }

    public Double getQuantiteLigneOrder() {
        return quantiteLigneOrder;
    }

    public void setQuantiteLigneOrder(Double quantiteLigneOrder) {
        this.quantiteLigneOrder = quantiteLigneOrder;
    }

    public Double getPrixUnitaireLigneOrder() {
        return prixUnitaireLigneOrder;
    }

    public void setPrixUnitaireLigneOrder(Double prixUnitaireLigneOrder) {
        this.prixUnitaireLigneOrder = prixUnitaireLigneOrder;
    }

    public String getStatusPaypalTransact() {
        return statusPaypalTransact;
    }

    public void setStatusPaypalTransact(String statusPaypalTransact) {
        this.statusPaypalTransact = statusPaypalTransact;
    }

    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public String getEmailProducer() {
        return emailProducer;
    }

    public void setEmailProducer(String emailProducer) {
        this.emailProducer = emailProducer;
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

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public Double getShippingCostLigneOrder() {
        return shippingCostLigneOrder;
    }

    public void setShippingCostLigneOrder(Double shippingCostLigneOrder) {
        this.shippingCostLigneOrder = shippingCostLigneOrder;
    }

    public String getDeliveryTimeLigneOrder() {
        return deliveryTimeLigneOrder;
    }

    public void setDeliveryTimeLigneOrder(String deliveryTimeLigneOrder) {
        this.deliveryTimeLigneOrder = deliveryTimeLigneOrder;
    }

    public int getIdDeliveryLigneOrder() {
        return idDeliveryLigneOrder;
    }

    public void setIdDeliveryLigneOrder(int idDeliveryLigneOrder) {
        this.idDeliveryLigneOrder = idDeliveryLigneOrder;
    }

    public String getNameDelivery() {
        return nameDelivery;
    }

    public void setNameDelivery(String nameDelivery) {
        this.nameDelivery = nameDelivery;
    }
}
