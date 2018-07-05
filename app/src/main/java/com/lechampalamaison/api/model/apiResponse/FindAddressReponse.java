package com.lechampalamaison.api.model.apiResponse;

public class FindAddressReponse {

    private int code;
    private String message;
    private AddressResult result;

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

    public AddressResult getResult() {
        return result;
    }

    public void setResult(AddressResult result) {
        this.result = result;
    }

    public class AddressResult{

        private String lastNameUser;
        private String firstNameUser;
        private String sexUser;
        private String addressUser;
        private String cityUser;
        private String cpUser;

        public AddressResult(String lastNameUser, String firstNameUser, String sexUser, String addressUser, String cityUser, String cpUser) {
            this.lastNameUser = lastNameUser;
            this.firstNameUser = firstNameUser;
            this.sexUser = sexUser;
            this.addressUser = addressUser;
            this.cityUser = cityUser;
            this.cpUser = cpUser;
        }

        public String getLastNameUser() {
            return lastNameUser;
        }

        public void setLastNameUser(String lastNameUser) {
            this.lastNameUser = lastNameUser;
        }

        public String getFirstNameUser() {
            return firstNameUser;
        }

        public void setFirstNameUser(String firstNameUser) {
            this.firstNameUser = firstNameUser;
        }

        public String getSexUser() {
            return sexUser;
        }

        public void setSexUser(String sexUser) {
            this.sexUser = sexUser;
        }

        public String getAddressUser() {
            return addressUser;
        }

        public void setAddressUser(String addressUser) {
            this.addressUser = addressUser;
        }

        public String getCityUser() {
            return cityUser;
        }

        public void setCityUser(String cityUser) {
            this.cityUser = cityUser;
        }

        public String getCpUser() {
            return cpUser;
        }

        public void setCpUser(String cpUser) {
            this.cpUser = cpUser;
        }
    }

}
