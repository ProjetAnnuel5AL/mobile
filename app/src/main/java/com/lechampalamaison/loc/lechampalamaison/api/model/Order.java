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
        private double prixU;
        private int qte;
        private double shippingCost;

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