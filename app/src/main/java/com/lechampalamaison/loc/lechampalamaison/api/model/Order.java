package com.lechampalamaison.loc.lechampalamaison.api.model;

import java.util.List;

public class Order {
    private String loginUser;
    private String token = "???";
    private List<Cart> cart;
    private PaymentDetail payementDetail;
    private DeliveryAddress address;

    public Order(String loginUser, String token, List<Cart> cart, PaymentDetail payementDetail, DeliveryAddress address) {
        this.loginUser = loginUser;
        this.token = token;
        this.cart = cart;
        this.payementDetail = payementDetail;
        this.address = address;
    }

    public Order() {
        // Empty constructor
    }

    public class Cart {
        private int id;
        private int qte;
        private Double qteMax;
        private String unit;
        private String category;
        private String product;
        private String title;
        private double prixU;
        private Double shippingCost;
        private String deliveryTime;
        private int idDelivery;
        private String img;

        public Cart(int id, int qte, Double qteMax, String unit, String category, String product, String title, double prixU, Double shippingCost, String deliveryTime, int idDelivery, String img) {
            this.id = id;
            this.qte = qte;
            this.qteMax = qteMax;
            this.unit = unit;
            this.category = category;
            this.product = product;
            this.title = title;
            this.prixU = prixU;
            this.shippingCost = shippingCost;
            this.deliveryTime = deliveryTime;
            this.idDelivery = idDelivery;
            this.img = img;
        }

        public Cart (int id, double prixU, int qte, double shippingCost) {
            this.id = id;
            this.prixU = prixU;
            this.qte = qte;
            this.shippingCost = shippingCost;
        }
    }

    public class PaymentDetail {
        private String paymentID;

        public PaymentDetail(String paymentID) {
            this.paymentID = paymentID;
        }
    }

    public class DeliveryAddress {
        private String lastNameUser;
        private String firstNameUser;
        private String sexUser;
        private String addressUser;
        private String cityUser;
        private String cpUser;

        public DeliveryAddress(String lastNameUser, String firstNameUser, String sexUser, String addressUser, String cityUser, String cpUser) {
            this.lastNameUser = lastNameUser;
            this.firstNameUser = firstNameUser;
            this.sexUser = sexUser;
            this.addressUser = addressUser;
            this.cityUser = cityUser;
            this.cpUser = cpUser;
        }
    }
}