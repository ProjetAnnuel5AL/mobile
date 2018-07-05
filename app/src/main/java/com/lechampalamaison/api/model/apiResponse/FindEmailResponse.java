package com.lechampalamaison.api.model.apiResponse;

public class FindEmailResponse {
    private int code;
    private String message;
    private FindEmailResult result;

    public FindEmailResponse(int code, String message, FindEmailResult result) {
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

    public FindEmailResult getResult() {
        return result;
    }

    public void setResult(FindEmailResult result) {
        this.result = result;
    }

    public class FindEmailResult {

        private String emailUser;

        public FindEmailResult(String emailUser) {
            this.emailUser = emailUser;
        }

        public String getEmailUser() {
            return emailUser;
        }

        public void setEmailUser(String emailUser) {
            this.emailUser = emailUser;
        }
    }

}
