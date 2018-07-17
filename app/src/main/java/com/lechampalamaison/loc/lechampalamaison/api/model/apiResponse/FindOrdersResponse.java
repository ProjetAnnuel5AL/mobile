package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

import java.util.Date;

public class FindOrdersResponse {
    private int code;
    private String message;
    private FindOrdersResult result;

    public FindOrdersResponse(int code, String message, FindOrdersResult result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FindOrdersResult getResult() {
        return result;
    }

    public void setResult(FindOrdersResult result) {
        this.result = result;
    }

    public class FindOrdersResult{

        private Orders[] orders;
        private String[] status;

        public FindOrdersResult(Orders[] orders, String[] status) {
            this.orders = orders;
            this.status = status;
        }

        public Orders[] getOrders() {
            return orders;
        }

        public void setOrders(Orders[] orders) {
            this.orders = orders;
        }

        public String[] getStatus() {
            return status;
        }

        public void setStatus(String[] status) {
            this.status = status;
        }

        public class Orders {

            private int idOrder;
            private Date dateOrder;
            private Double totalOrder;

            public Orders(int idOrder, Date dateOrder, Double totalOrder) {
                this.idOrder = idOrder;
                this.dateOrder = dateOrder;
                this.totalOrder = totalOrder;
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


        }


    }

}
