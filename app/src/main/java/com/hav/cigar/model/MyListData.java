package com.hav.cigar.model;

public class MyListData {
    private int id;
    private String description;
    private String img;
    private String name;
    private String price;
    private String size;
    private int category_id;
    private int product_id;
    private int wishlist_id;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    private int cart_id;
    public MyListData(){

    }
    public MyListData(int id,String description, String img,String name,String price) {
        this.id = id;
        this.description = description;
        this.img = img;
        this.name = name;
        this.price = price;
    }
    public int getId() {
        return id; }
    public void setId(int id) {
        this.id = id; }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImgId() {
        return img;
    }
    public void setImgId(String imgId) {
        this.img = imgId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
