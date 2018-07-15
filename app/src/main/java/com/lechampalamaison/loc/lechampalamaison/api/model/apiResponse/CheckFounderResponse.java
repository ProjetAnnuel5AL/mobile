package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class CheckFounderResponse {

    private int code;
    private String message;
    private CheckFounderResult[] result;

    public CheckFounderResponse(int code, String message, CheckFounderResult[] result) {
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

    public CheckFounderResult[] getResult() {
        return result;
    }

    public void setResult(CheckFounderResult[] result) {
        this.result = result;
    }

    public class CheckFounderResult {

        private int id;

        public CheckFounderResult(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
