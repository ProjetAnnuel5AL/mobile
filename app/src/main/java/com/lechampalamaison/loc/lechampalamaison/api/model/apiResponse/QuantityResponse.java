package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class QuantityResponse {

    private int code;
    private String message;
    private QuantityResult[] result;

    public QuantityResponse(int code, String message, QuantityResult[] result) {
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

    public QuantityResult[] getResult() {
        return result;
    }

    public void setResult(QuantityResult[] result) {
        this.result = result;
    }

    public class QuantityResult{

        private int idItem;
        private float quantityItem;

        public QuantityResult(int idItem, float quantityItem) {
            this.idItem = idItem;
            this.quantityItem = quantityItem;
        }

        public int getIdItem() {
            return idItem;
        }

        public void setIdItem(int idItem) {
            this.idItem = idItem;
        }

        public float getQuantityItem() {
            return quantityItem;
        }

        public void setQuantityItem(float quantityItem) {
            this.quantityItem = quantityItem;
        }
    }

}
