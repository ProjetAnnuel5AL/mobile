package com.lechampalamaison.api.model;

public class Order {
    private String token = "???";
    private Cart cart;
    private PaymentDetail paymentDetail;
    private DeliveryAddress address;

    private class Cart {
        private int length;
        private double prixU;
        private int qte;
        private double shippingCost;

        public Cart (int length, double prixU, int qte, double shippingCost) {
            this.length = length;
            this.prixU = prixU;
            this.qte = qte;
            this.shippingCost = shippingCost;
        }
    }

    private class PaymentDetail {
        private String paymentID;
        private String payerID;

        public PaymentDetail(String paymentID, String payerID) {
            this.paymentID = paymentID;
            this.payerID = payerID;
        }
    }

    private class DeliveryAddress {
        private String lastNameUser;
        private String firstNameUser;
        private String sexUser;
        private String addressUser;
        private String cityUser;
        private int cpUser;

        public DeliveryAddress(String lastNameUser, String firstNameUser, String sexUser, String addressUser, String cityUser, int cpUser) {
            this.lastNameUser = lastNameUser;
            this.firstNameUser = firstNameUser;
            this.sexUser = sexUser;
            this.addressUser = addressUser;
            this.cityUser = cityUser;
            this.cpUser = cpUser;
        }
    }
}
