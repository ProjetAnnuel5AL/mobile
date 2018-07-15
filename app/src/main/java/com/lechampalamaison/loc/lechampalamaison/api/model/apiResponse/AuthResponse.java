package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class AuthResponse {
    private int code;
    private String message;
    private AuthResult result;

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

    public AuthResult getResult() {
        return result;
    }

    public void setResult(AuthResult result) {
        this.result = result;
    }

    public class AuthResult {
        private String loginUser;
        private String emailUser;
        private int typeUser;
        private String token;

        public String getLoginUser() {
            return loginUser;
        }

        public void setLoginUser(String loginUser) {
            this.loginUser = loginUser;
        }

        public String getEmailUser() {
            return emailUser;
        }

        public void setEmailUser(String emailUser) {
            this.emailUser = emailUser;
        }

        public int getTypeUser() {
            return typeUser;
        }

        public void setTypeUser(int typeUser) {
            this.typeUser = typeUser;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}