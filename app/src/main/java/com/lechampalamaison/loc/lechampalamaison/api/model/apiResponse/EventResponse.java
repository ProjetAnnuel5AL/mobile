package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

import java.util.Date;

public class EventResponse {

    private int code;
    private String message;
    private EventResult result;

    public EventResponse(int code, String message, EventResult result) {
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

    public EventResult getResult() {
        return result;
    }

    public void setResult(EventResult result) {
        this.result = result;
    }

    public class EventResult{

        private int idEvent;
        private String nameEvent;
        private String descriptionEvent;
        private Date dateEvent;
        private String adressEvent;
        private Double latEvent;
        private Double longEvent;

        public EventResult(int idEvent, String nameEvent, String descriptionEvent, Date dateEvent, String adressEvent, Double latEvent, Double longEvent) {
            this.idEvent = idEvent;
            this.nameEvent = nameEvent;
            this.descriptionEvent = descriptionEvent;
            this.dateEvent = dateEvent;
            this.adressEvent = adressEvent;
            this.latEvent = latEvent;
            this.longEvent = longEvent;
        }

        public String getAdressEvent() {
            return adressEvent;
        }

        public void setAdressEvent(String adressEvent) {
            this.adressEvent = adressEvent;
        }

        public Double getLatEvent() {
            return latEvent;
        }

        public void setLatEvent(Double latEvent) {
            this.latEvent = latEvent;
        }

        public Double getLongEvent() {
            return longEvent;
        }

        public void setLongEvent(Double longEvent) {
            this.longEvent = longEvent;
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
