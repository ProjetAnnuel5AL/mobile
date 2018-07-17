package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class CategoryResponse {

    private int code;
    private String message;
    private CategoryResult[] result;

    public CategoryResponse(int code, String message, CategoryResult[] result) {
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

    public CategoryResult[] getResult() {
        return result;
    }

    public void setResult(CategoryResult[] result) {
        this.result = result;
    }

    public class CategoryResult{

        private int idCategory;
        private String nameCategory;

        public CategoryResult(int idCategory, String nameCategory) {
            this.idCategory = idCategory;
            this.nameCategory = nameCategory;
        }

        public int getIdCategory() {
            return idCategory;
        }

        public void setIdCategory(int idCategory) {
            this.idCategory = idCategory;
        }

        public String getNameCategory() {
            return nameCategory;
        }

        public void setNameCategory(String nameCategory) {
            this.nameCategory = nameCategory;
        }
    }

}
