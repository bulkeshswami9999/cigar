package com.hav.cigar.model;

public class OrderListData {
    private String image_description;
    private int imageId;
    public OrderListData(String image_description, int imageId) {
        this.image_description = image_description;
        this.imageId = imageId;
    }
    public String getImage_description() {
        return image_description;
    }
    public void setImage_description(String image_description) {
        this.image_description = image_description;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
