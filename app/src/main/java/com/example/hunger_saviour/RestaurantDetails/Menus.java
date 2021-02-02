package com.example.hunger_saviour.RestaurantDetails;

public class Menus {
    private String name;
    private String description;
    private String rate;
    private String image;

    public Menus() {
    }

    public Menus(String name, String description, String rate, String image) {
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.image = image;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}