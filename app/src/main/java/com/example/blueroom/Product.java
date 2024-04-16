package com.example.blueroom;

public class Product {
    public String author;  // Cambiado a minúscula
    public String imageURL;
    public String name;    // Cambiado a minúscula
    public int quantity;   // Cambiado a minúscula
    public int price;      // Cambiado a minúscula
    public String date;
    public String type;
    public String[] tag;

    public Product() {}

    public Product(String imageURL, String name, String author, int price, int quantity) {
        this.imageURL = imageURL;
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String author, String imageURL, String name, int quantity, int price, String date, String type, String[] tag) {
        this.author = author;
        this.imageURL = imageURL;
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
        this.imageURL = imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDate(String date) {
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
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
