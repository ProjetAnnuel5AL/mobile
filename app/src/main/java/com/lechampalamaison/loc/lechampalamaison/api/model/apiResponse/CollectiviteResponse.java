package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class CollectiviteResponse {

    private int code;
    private String message;
    private CollectiviteResult result;

    public CollectiviteResponse(int code, String message, CollectiviteResult result) {
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

    public CollectiviteResult getResult() {
        return result;
    }

    public void setResult(CollectiviteResult result) {
        this.result = result;
    }

    public class CollectiviteResult {

        private int id;
        private int founderUserId;
        private String avatar;
        private String name;
        private String email;
        private String phone;
        private String adress;
        private String city;
        private String localtion;
        private Double latGroup;
        private Double longGroup;
        private String description;
        private int idProducer;
        private String lastNameProducer;
        private String firstNameProducer;
        private String cpProducer;
        private String avatarProducer;
        private String producersGroup;
        private int countMembers;

        public CollectiviteResult(int id, int founderUserId, String avatar, String name, String email, String phone, String adress, String city, String localtion, Double latGroup, Double longGroup, String description, int idProducer, String lastNameProducer, String firstNameProducer, String cpProducer, String avatarProducer, String producersGroup, int countMembers) {
            this.id = id;
            this.founderUserId = founderUserId;
            this.avatar = avatar;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.adress = adress;
            this.city = city;
            this.localtion = localtion;
            this.latGroup = latGroup;
            this.longGroup = longGroup;
            this.description = description;
            this.idProducer = idProducer;
            this.lastNameProducer = lastNameProducer;
            this.firstNameProducer = firstNameProducer;
            this.cpProducer = cpProducer;
            this.avatarProducer = avatarProducer;
            this.producersGroup = producersGroup;
            this.countMembers = countMembers;
        }

        public int getIdProducer() {
            return idProducer;
        }

        public void setIdProducer(int idProducer) {
            this.idProducer = idProducer;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFounderUserId() {
            return founderUserId;
        }

        public void setFounderUserId(int founderUserId) {
            this.founderUserId = founderUserId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLocaltion() {
            return localtion;
        }

        public void setLocaltion(String localtion) {
            this.localtion = localtion;
        }

        public Double getLatGroup() {
            return latGroup;
        }

        public void setLatGroup(Double latGroup) {
            this.latGroup = latGroup;
        }

        public Double getLongGroup() {
            return longGroup;
        }

        public void setLongGroup(Double longGroup) {
            this.longGroup = longGroup;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getAvatarProducer() {
            return avatarProducer;
        }

        public void setAvatarProducer(String avatarProducer) {
            this.avatarProducer = avatarProducer;
        }

        public String getProducersGroup() {
            return producersGroup;
        }

        public void setProducersGroup(String producersGroup) {
            this.producersGroup = producersGroup;
        }

        public int getCountMembers() {
            return countMembers;
        }

        public void setCountMembers(int countMembers) {
            this.countMembers = countMembers;
        }
    }


}
