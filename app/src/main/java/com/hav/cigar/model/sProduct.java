package com.hav.cigar.model;

import android.content.Context;

public class sProduct {
    private int id;
    private String name;
    private String description;
    private String original_price;
    private String image;
    private Context context;

    public sProduct() {
        this.id = id;
        this.name = name;
        this.description = description;
        this.original_price = original_price;
        this.image = image;
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


