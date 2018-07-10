package com.lechampalamaison.api.model.apiResponse;

public class ItemResponse {
    private int code;
    private String message;
    private ItemResult result;

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

    public ItemResult getResult() {
        return result;
    }

    public void setResult(ItemResult result) {
        this.result = result;
    }

    public class ItemResult {

        private InfoItem infoItem;
        private Stars[] stars;

        public InfoItem getInfoItem() {
            return infoItem;
        }

        public void setInfoItem(InfoItem infoItem) {
            this.infoItem = infoItem;
        }

        public Stars[] getStars() {
            return stars;
        }

        public void setStars(Stars[] stars) {
            this.stars = stars;
        }

        public class InfoItem {

            private int idItem;
            private Double priceItem;
            private String addressItem;
            private String descriptionItem;
            private String locationItem;
            private String cityItem;
            private String cpItem;
            private int quantityItem;
            private String nameItem;
            private String fileExtensionsItem;
            private String loginUser;
            private String nameCategory;
            private String nameProduct;
            private int idCategory;
            private int idProduct;
            private int idUnit;
            private String nameUnit;
            private int idProducer;
            private String lastNameProducer;
            private String firstNameProducer;
            private String photoURL;
            private Double shippingCostItem;
            private Double quatityMaxOrderItem;

            public Double getShippingCostItem() {
                return shippingCostItem;
            }

            public void setShippingCostItem(Double shippingCostItem) {
                this.shippingCostItem = shippingCostItem;
            }

            public Double getQuatityMaxOrderItem() {
                return quatityMaxOrderItem;
            }

            public void setQuatityMaxOrderItem(Double quatityMaxOrderItem) {
                this.quatityMaxOrderItem = quatityMaxOrderItem;
            }

            public int getIdItem() {
                return idItem;
            }

            public void setIdItem(int idItem) {
                this.idItem = idItem;
            }

            public Double getPriceItem() {
                return priceItem;
            }

            public void setPriceItem(Double priceItem) {
                this.priceItem = priceItem;
            }

            public String getAddressItem() {
                return addressItem;
            }

            public void setAddressItem(String addressItem) {
                this.addressItem = addressItem;
            }

            public String getDescriptionItem() {
                return descriptionItem;
            }

            public void setDescriptionItem(String descriptionItem) {
                this.descriptionItem = descriptionItem;
            }

            public String getLocationItem() {
                return locationItem;
            }

            public void setLocationItem(String locationItem) {
                this.locationItem = locationItem;
            }

            public String getCityItem() {
                return cityItem;
            }

            public void setCityItem(String cityItem) {
                this.cityItem = cityItem;
            }

            public String getCpItem() {
                return cpItem;
            }

            public void setCpItem(String cpItem) {
                this.cpItem = cpItem;
            }

            public int getQuantityItem() {
                return quantityItem;
            }

            public void setQuantityItem(int quantityItem) {
                this.quantityItem = quantityItem;
            }

            public String getNameItem() {
                return nameItem;
            }

            public void setNameItem(String nameItem) {
                this.nameItem = nameItem;
            }

            public String getFileExtensionsItem() {
                return fileExtensionsItem;
            }

            public void setFileExtensionsItem(String fileExtensionsItem) {
                this.fileExtensionsItem = fileExtensionsItem;
            }

            public String getLoginUser() {
                return loginUser;
            }

            public void setLoginUser(String loginUser) {
                this.loginUser = loginUser;
            }

            public String getNameCategory() {
                return nameCategory;
            }

            public void setNameCategory(String nameCategory) {
                this.nameCategory = nameCategory;
            }

            public String getNameProduct() {
                return nameProduct;
            }

            public void setNameProduct(String nameProduct) {
                this.nameProduct = nameProduct;
            }

            public int getIdCategory() {
                return idCategory;
            }

            public void setIdCategory(int idCategory) {
                this.idCategory = idCategory;
            }

            public int getIdProduct() {
                return idProduct;
            }

            public void setIdProduct(int idProduct) {
                this.idProduct = idProduct;
            }

            public int getIdUnit() {
                return idUnit;
            }

            public void setIdUnit(int idUnit) {
                this.idUnit = idUnit;
            }

            public String getNameUnit() {
                return nameUnit;
            }

            public void setNameUnit(String nameUnit) {
                this.nameUnit = nameUnit;
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

            public String getPhotoURL() {
                return photoURL;
            }

            public void setPhotoURL(String photoURL) {
                this.photoURL = photoURL;
            }
        }

        public class Stars {
            private int starComment;

            public int getStarComment() {
                return starComment;
            }

            public void setStarComment(int starComment) {
                this.starComment = starComment;
            }
        }

    }

}
