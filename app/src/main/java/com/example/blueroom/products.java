package com.example.blueroom;

public class products {
    public String author;
    public String imageUrl;
    public String name;
    public float quantity;
    public float price;
    public int date;
    public String type;
    public String[] tag;

    public products() {}

    public products(String imageurl, String name, String author, float price, float quantity) {
        this.imageUrl = imageurl;
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public products(String author, String imageURL, String name, float quantity, float price, int date, String type, String[] tag) {
        this.author = author;
        this.imageUrl = imageUrl;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.type = type;
        this.tag = tag;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImageURL(String imageURL) {
        this.imageUrl = imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }
}
