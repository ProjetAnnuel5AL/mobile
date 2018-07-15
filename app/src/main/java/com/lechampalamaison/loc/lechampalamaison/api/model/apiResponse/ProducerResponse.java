package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

import java.util.Date;

public class ProducerResponse {

    private int code;
    private String message;
    private ProducerResult result;

    public ProducerResponse(int code, String message, ProducerResult result) {
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

    public ProducerResult getResult() {
        return result;
    }

    public void setResult(ProducerResult result) {
        this.result = result;
    }

    public class ProducerResult{

        private String lastNameProducer;
        private String firstNameProducer;
        private String descriptionProducer;
        private String avatarProducer;
        private String loginUser;
        private String addressProducer;
        private String cityProducer;
        private String locationProducer;
        private Comment[] comment;

        public ProducerResult(String lastNameProducer, String firstNameProducer, String descriptionProducer, String avatarProducer, String loginUser, String addressProducer, String cityProducer, String locationProducer, Comment[] comment) {
            this.lastNameProducer = lastNameProducer;
            this.firstNameProducer = firstNameProducer;
            this.descriptionProducer = descriptionProducer;
            this.avatarProducer = avatarProducer;
            this.loginUser = loginUser;
            this.addressProducer = addressProducer;
            this.cityProducer = cityProducer;
            this.locationProducer = locationProducer;
            this.comment = comment;
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

        public String getDescriptionProducer() {
            return descriptionProducer;
        }

        public void setDescriptionProducer(String descriptionProducer) {
            this.descriptionProducer = descriptionProducer;
        }

        public String getAvatarProducer() {
            return avatarProducer;
        }

        public void setAvatarProducer(String avatarProducer) {
            this.avatarProducer = avatarProducer;
        }

        public String getLoginUser() {
            return loginUser;
        }

        public void setLoginUser(String loginUser) {
            this.loginUser = loginUser;
        }

        public String getAddressProducer() {
            return addressProducer;
        }

        public void setAddressProducer(String addressProducer) {
            this.addressProducer = addressProducer;
        }

        public String getCityProducer() {
            return cityProducer;
        }

        public void setCityProducer(String cityProducer) {
            this.cityProducer = cityProducer;
        }

        public String getLocationProducer() {
            return locationProducer;
        }

        public void setLocationProducer(String locationProducer) {
            this.locationProducer = locationProducer;
        }

        public Comment[]  getComment() {
            return comment;
        }

        public void setComment(Comment[]  comment) {
            this.comment = comment;
        }

        public class Comment{

            private String comment;
            private Double starComment;
            private Date dateComment;
            private String loginUser;

            public Comment(String comment, Double starComment, Date dateComment, String loginUser) {
                this.comment = comment;
                this.starComment = starComment;
                this.dateComment = dateComment;
                this.loginUser = loginUser;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public Double getStarComment() {
                return starComment;
            }

            public void setStarComment(Double starComment) {
                this.starComment = starComment;
            }

            public Date getDateComment() {
                return dateComment;
            }

            public void setDateComment(Date dateComment) {
                this.dateComment = dateComment;
            }

            public String getLoginUser() {
                return loginUser;
            }

            public void setLoginUser(String loginUser) {
                this.loginUser = loginUser;
            }
        }
    }

}
