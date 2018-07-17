package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

import java.util.Date;

public class ProducerGroupEventResponse {
    private int code;
    private String message;
    private ProducerGroupEventResult[] result;

    public ProducerGroupEventResponse(int code, String message, ProducerGroupEventResult[] result) {
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

    public ProducerGroupEventResult[] getResult() {
        return result;
    }

    public void setResult(ProducerGroupEventResult[] result) {
        this.result = result;
    }

    public class ProducerGroupEventResult{

        private int idEvent;
        private String nameEvent;
        private String descriptionEvent;
        private Date dateEvent;

        public ProducerGroupEventResult(int idEvent, String nameEvent, String descriptionEvent, Date dateEvent) {
            this.idEvent = idEvent;
            this.nameEvent = nameEvent;
            this.descriptionEvent = descriptionEvent;
            this.dateEvent = dateEvent;
        }

        public int getIdEvent() {
            return idEvent;
        }

        public void setIdEvent(int idEvent) {
            this.idEvent = idEvent;
        }

        public String getNameEvent() {
            return nameEvent;
        }

        public void setNameEvent(String nameEvent) {
            this.nameEvent = nameEvent;
        }

        public String getDescriptionEvent() {
            return descriptionEvent;
        }

        public void setDescriptionEvent(String descriptionEvent) {
            this.descriptionEvent = descriptionEvent;
        }

        public Date getDateEvent() {
            return dateEvent;
        }

        public void setDateEvent(Date dateEvent) {
            this.dateEvent = dateEvent;
        }
    }

}
