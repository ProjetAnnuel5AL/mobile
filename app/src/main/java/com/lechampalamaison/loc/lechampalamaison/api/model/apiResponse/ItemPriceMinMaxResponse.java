package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class ItemPriceMinMaxResponse {

    private int code;
    private String message;
    private ItemPriceMinMaxResult result;

    public ItemPriceMinMaxResponse(int code, String message, ItemPriceMinMaxResult result) {
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

    public ItemPriceMinMaxResult getResult() {
        return result;
    }

    public void setResult(ItemPriceMinMaxResult result) {
        this.result = result;
    }

    public class ItemPriceMinMaxResult {
        private Double maxPrice;
        private Double minPrice;

        public ItemPriceMinMaxResult(Double maxPrice, Double minPrice) {
            this.maxPrice = maxPrice;
            this.minPrice = minPrice;
        }

        public Double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(Double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public Double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Double minPrice) {
            this.minPrice = minPrice;
        }
    }


}
