package com.example.hunger_saviour.Cart;

public class cart {
    private String Foodkey,name,price,quantity,discount;

    public cart() {
    }

    public cart(String foodkey, String name, String price, String quantity, String discount) {
        Foodkey = foodkey;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getFoodkey() {
        return Foodkey;
    }

    public void setFoodkey(String foodkey) {
        Foodkey = foodkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() { return price; }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
