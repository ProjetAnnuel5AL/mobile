package com.lechampalamaison.loc.lechampalamaison.api.model;

public class ItemApi {

    private String manualSearch;
    private int category;
    private int product;
    private Double lat;
    private Double lng;
    private Number priceMin;
    private Number priceMax;
    private int limit;

    public ItemApi(int limit) {
        this.manualSearch = manualSearch;
        this.category = category;
        this.product = product;
        this.lat = lat;
        this.lng = lng;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.limit = limit;
    }

    public ItemApi(int limit, String manualSearch, int category, int product, Double lat, Double lng, Number priceMin, Number priceMax) {
        this.manualSearch = manualSearch;
        this.category = category;
        this.product = product;
        this.lat = lat;
        this.lng = lng;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.limit = limit;
    }

    public String getManualSearch() {
        return manualSearch;
    }

    public void setManualSearch(String manualSearch) {
        this.manualSearch = manualSearch;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Number getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Number priceMin) {
        this.priceMin = priceMin;
    }

    public Number getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Number priceMax) {
        this.priceMax = priceMax;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
