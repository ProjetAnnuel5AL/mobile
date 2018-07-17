package com.lechampalamaison.loc.lechampalamaison.Model;

public class Product {

    private int idProduct;
    private String nameProduct;
    private int idCategoryProduct;

    public Product(int idProduct, String nameProduct, int idCategoryProduct) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.idCategoryProduct = idCategoryProduct;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getIdCategoryProduct() {
        return idCategoryProduct;
    }

    public void setIdCategoryProduct(int idCategoryProduct) {
        this.idCategoryProduct = idCategoryProduct;
    }

}
