package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class ProducerGroupSubscriberCheckResponse {

    private int code;
    private String message;
    private ProducerGroupSubscriberCheckResult[] result;

    public ProducerGroupSubscriberCheckResponse(int code, String message, ProducerGroupSubscriberCheckResult[] result) {
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

    public ProducerGroupSubscriberCheckResult[] getResult() {
        return result;
    }

    public void setResult(ProducerGroupSubscriberCheckResult[] result) {
        this.result = result;
    }

    public class ProducerGroupSubscriberCheckResult {

        private int id;
        private int idUser;
        private int idGroup;

        public ProducerGroupSubscriberCheckResult(int id, int idUser, int idGroup) {
            this.id = id;
            this.idUser = idUser;
            this.idGroup = idGroup;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdUser() {
            return idUser;
        }

        public void setIdUser(int idUser) {
            this.idUser = idUser;
        }

        public int getIdGroup() {
            return idGroup;
        }

        public void setIdGroup(int idGroup) {
            this.idGroup = idGroup;
        }

    }
}
