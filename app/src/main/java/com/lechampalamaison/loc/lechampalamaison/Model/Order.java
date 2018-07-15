package com.lechampalamaison.loc.lechampalamaison.Model;

import java.util.Date;

public class Order {

    private int idOrder;
    private Date dateOrder;
    private Double totalOrder;
    private String status;
    private LigneOrder[] ligneOrders;

    public Order(int idOrder, Date dateOrder, Double totalOrder, String status) {
        this.idOrder = idOrder;
        this.dateOrder = dateOrder;
        this.totalOrder = totalOrder;
        this.status = status;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LigneOrder[] getLigneOrders() {
        return ligneOrders;
    }

    public void setLigneOrders(LigneOrder[] ligneOrders) {
        this.ligneOrders = ligneOrders;
    }
}
