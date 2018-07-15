package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class ProducerGroupMemberResponse {
    private int code;
    private String message;
    private ProducerGroupMemberResult[] result;

    public ProducerGroupMemberResponse(int code, String message, ProducerGroupMemberResult[] result) {
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

    public ProducerGroupMemberResult[] getResult() {
        return result;
    }

    public void setResult(ProducerGroupMemberResult[] result) {
        this.result = result;
    }

    public class ProducerGroupMemberResult{

        private int idProducer;
        private String lastNameProducer;
        private String firstNameProducer;
        private String cpProducer;
        private String avatarProducer;

        public ProducerGroupMemberResult(int idProducer, String lastNameProducer, String firstNameProducer, String cpProducer, String avatarProducer) {
            this.idProducer = idProducer;
            this.lastNameProducer = lastNameProducer;
            this.firstNameProducer = firstNameProducer;
            this.cpProducer = cpProducer;
            this.avatarProducer = avatarProducer;
        }

        public String getAvatarProducer() {
            return avatarProducer;
        }

        public void setAvatarProducer(String avatarProducer) {
            this.avatarProducer = avatarProducer;
        }

        public int getIdProducer() {
            return idProducer;
        }

        public void setIdProducer(int idProducer) {
            this.idProducer = idProducer;
        }

        public String getLastNameProducer() {
            return lastNameProducer;
        }

        public void setLastNameProducer(String lastNameProducer) {
            this.lastNameProducer = lastNameProducer;
        }

        public String getFirstNameProducer() {
            return firstNameProducer;
        }

        public void setFirstNameProducer(String firstNameProducer) {
            this.firstNameProducer = firstNameProducer;
        }

        public String getCpProducer() {
            return cpProducer;
        }

        public void setCpProducer(String cpProducer) {
            this.cpProducer = cpProducer;
        }
    }

}
