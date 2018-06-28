package com.lechampalamaison.model;

import com.lechampalamaison.api.utils.Configuration;

public class Item {
    String urlMainImg;
    private int id;
    private String title;
    private String description;
    private String localisation;
    private double price;
    private String fileExtensionsItem;

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

    public String getTitle() {
        return title;
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
}
