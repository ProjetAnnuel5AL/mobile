package com.lechampalamaison.loc.lechampalamaison.Model;


import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;

import java.util.Objects;

public class Item {
    String urlMainImg;
    private int id;
    private int qte;
    private Double qteMax;
    private String unit;
    private String category;
    private String product;
    private String title;
    private double price;
    private double PrixU;
    private double shippingCost;
    private String deliveryTime;
    private int idDeliveryItem;

    private String description;
    private String localisation;

    private String fileExtensionsItem;


    public Item(String urlMainImg, int id, int qte, Double qteMax, String unit, String category, String product, String title, double price, double prixU, double shippingCost, int idDeliveryItem, String deliveryTime, String description, String localisation, String fileExtensionsItem) {
        this.urlMainImg = urlMainImg;
        this.id = id;
        this.qte = qte;
        this.qteMax = qteMax;
        this.unit = unit;
        this.category = category;
        this.product = product;
        this.title = title;
        this.price = price;
        PrixU = prixU;
        this.shippingCost = shippingCost;
        this.deliveryTime = deliveryTime;
        this.description = description;
        this.localisation = localisation;
        this.fileExtensionsItem = fileExtensionsItem;
        this.idDeliveryItem = idDeliveryItem;
    }

    public Item(int id, String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.id = id;
    }



    public Item(int id, String title, String description, String localisation, double prix, String fileExtensionsItem) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.localisation = localisation;
        this.price = prix;
        this.fileExtensionsItem = fileExtensionsItem;
        String[] ext = fileExtensionsItem.split(";");
        this.urlMainImg = Configuration.urlApi + "itemPhotos/" + id + "/0."+ext[0];
    }

    public int getIdDeliveryItem() {
        return idDeliveryItem;
    }

    public void setIdDeliveryItem(int idDeliveryItem) {
        this.idDeliveryItem = idDeliveryItem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }


    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Double getQteMax() {
        return qteMax;
    }

    public void setQteMax(Double qteMax) {
        this.qteMax = qteMax;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrixU() {
        return PrixU;
    }

    public void setPrixU(double prixU) {
        PrixU = prixU;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getUrlMainImg() {
        return urlMainImg;
    }

    public void setUrlMainImg(String urlMainImg) {
        this.urlMainImg = urlMainImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFileExtensionsItem() {
        return fileExtensionsItem;
    }

    public void setFileExtensionsItem(String fileExtensionsItem) {
        this.fileExtensionsItem = fileExtensionsItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                Double.compare(item.price, price) == 0 &&
                Objects.equals(urlMainImg, item.urlMainImg) &&
                Objects.equals(title, item.title) &&
                Objects.equals(description, item.description) &&
                Objects.equals(localisation, item.localisation) &&
                Objects.equals(fileExtensionsItem, item.fileExtensionsItem);
    }

    @Override
    public int hashCode() {

        return Objects.hash(urlMainImg, id, title, description, localisation, price, fileExtensionsItem);
    }
}
