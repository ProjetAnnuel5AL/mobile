package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class ProducerGroupEventParticiantResponse {

    private int code;
    private String message;
    private ProducerGroupEventParticiantResult[] result;

    public ProducerGroupEventParticiantResponse(int code, String message, ProducerGroupEventParticiantResult[] result) {
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

    public ProducerGroupEventParticiantResult[] getResult() {
        return result;
    }

    public void setResult(ProducerGroupEventParticiantResult[] result) {
        this.result = result;
    }

    public class ProducerGroupEventParticiantResult{

        private int idProducer;
        private String firstNameProducer;
        private String lastNameProducer;
        private String cpProducer;
        private String libelleParticipant;
        private String avatarProducer;

        public ProducerGroupEventParticiantResult(int idProducer, String firstNameProducer, String lastNameProducer, String cpProducer, String libelleParticipant, String avatarProducer) {
            this.idProducer = idProducer;
            this.firstNameProducer = firstNameProducer;
            this.lastNameProducer = lastNameProducer;
            this.cpProducer = cpProducer;
            this.libelleParticipant = libelleParticipant;
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

        public String getFirstNameProducer() {
            return firstNameProducer;
        }

        public void setFirstNameProducer(String firstNameProducer) {
            this.firstNameProducer = firstNameProducer;
        }

        public String getLastNameProducer() {
            return lastNameProducer;
        }

        public void setLastNameProducer(String lastNameProducer) {
            this.lastNameProducer = lastNameProducer;
        }

        public String getCpProducer() {
            return cpProducer;
        }

        public void setCpProducer(String cpProducer) {
            this.cpProducer = cpProducer;
        }

        public String getLibelleParticipant() {
            return libelleParticipant;
        }

        public void setLibelleParticipant(String libelleParticipant) {
            this.libelleParticipant = libelleParticipant;
        }


    }


}
