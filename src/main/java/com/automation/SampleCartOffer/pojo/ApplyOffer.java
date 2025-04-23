package com.automation.SampleCartOffer.pojo;

public class ApplyOffer {

    private int restaurant_id;
    private int user_id;
    private double cart_value;


    public ApplyOffer(int restaurant_id, int user_id, double cart_value) {
        this.restaurant_id = restaurant_id;
        this.user_id = user_id;
        this.cart_value = cart_value;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getCart_value() {
        return cart_value;
    }

    public void setCart_value(double cart_value) {
        this.cart_value = cart_value;
    }
}