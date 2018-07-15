package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class ProductResponse {

    private int code;
    private String message;
    private ProductResult[] result;

    public ProductResponse(int code, String message, ProductResult[] result) {
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

    public ProductResult[] getResult() {
        return result;
    }

    public void setResult(ProductResult[] result) {
        this.result = result;
    }

    public class ProductResult{

        private int idProduct;
        private String nameProduct;
        private int idCategoryProduct;

        public ProductResult(int idProduct, String nameProduct, int idCategoryProduct) {
            this.idProduct = idProduct;
            this.nameProduct = nameProduct;
            this.idCategoryProduct = idCategoryProduct;
        }

        public int getIdProduct() {
            return idProduct;
        }

        public void setIdProduct(int idProduct) {
            this.idProduct = idProduct;
        }

        public String getNameProduct() {
            return nameProduct;
        }

        public void setNameProduct(String nameProduct) {
            this.nameProduct = nameProduct;
        }

        public int getIdCategoryProduct() {
            return idCategoryProduct;
        }

        public void setIdCategoryProduct(int idCategoryProduct) {
            this.idCategoryProduct = idCategoryProduct;
        }
    }

}
