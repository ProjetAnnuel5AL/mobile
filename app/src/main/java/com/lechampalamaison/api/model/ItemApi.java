package com.lechampalamaison.api.model;

public class ItemApi {

    private String manualSearch;
    private String category;
    private String product;
    private Double lat;
    private Double lng;
    private Double priceMin;
    private Double priceMax;
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

    public ItemApi(int limit, String manualSearch, String category, String product, Double lat, Double lng, Double priceMin, Double priceMax) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
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

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
