package com.lechampalamaison.api.model.apiResponse;

public class QuantityResponse {
    private int code;
    private String message;
    private QuantityResult quantityResult;

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

    public QuantityResult getQuantityResult() {
        return quantityResult;
    }

    public void setQuantityResult(QuantityResult quantityResult) {
        this.quantityResult = quantityResult;
    }

    public class QuantityResult {
        private int quantity;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
