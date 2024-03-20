package com.example.doan.model;

public class Post {
    public String image;
    public String price;
    public String title;

    public Post( String image, String price, String title) {
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
