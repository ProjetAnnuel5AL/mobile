package com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse;

public class ItemsResponse {
    private int code;
    private String message;
    private ItemsResult result;

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

    public ItemsResult getResult() {
        return result;
    }

    public void setResult(ItemsResult result) {
        this.result = result;
    }

    public class ItemsResult {
       private int nbTotalItem;
       private listItems[] list;

        public int getNbTotalItem() {
            return nbTotalItem;
        }

        public void setNbTotalItem(int nbTotalItem) {
            this.nbTotalItem = nbTotalItem;
        }


        public listItems[] getList() {
            return list;
        }

        public void setList(listItems[] list) {
            this.list = list;
        }

        public class listItems {
            private int idItem;
            private double priceItem;
            private String locationItem;
            private String cityItem;
            private String cpItem;
            private int quantityItem;
            private String nameItem;
            private String fileExtensionsItem;
            private String descriptionItem;
            private String loginUser;
            private String nameCategory;
            private String nameProduct;
            private int idCategory;
            private int idProduct;
            private String nameUnit;
            private int idProducer;

            public int getIdItem() {
                return idItem;
            }

            public void setIdItem(int idItem) {
                this.idItem = idItem;
            }

            public double getPriceItem() {
                return priceItem;
            }

            public void setPriceItem(double priceItem) {
                this.priceItem = priceItem;
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

            public String getDescriptionItem() {
                return descriptionItem;
            }

            public void setDescriptionItem(String descriptionItem) {
                this.descriptionItem = descriptionItem;
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
        }
    }
}
