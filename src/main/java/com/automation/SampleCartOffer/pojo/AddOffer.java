package com.automation.SampleCartOffer.pojo;

public class AddOffer {

    private int restaurant_id;
    private String offer_type;
    private double offer_value;
    private String[] customer_segment;

    public AddOffer(int restaurant_id, String offer_type, double offer_value, String[] customer_segment) {
        this.restaurant_id = restaurant_id;
        this.offer_type = offer_type;
        this.offer_value = offer_value;
        this.customer_segment = customer_segment;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public void setOffer_type(String offer_type) {
        this.offer_type = offer_type;
    }

    public double getOffer_value() {
        return offer_value;
    }

    public void setOffer_value(double offer_value) {
        this.offer_value = offer_value;
    }

    public String[] getCustomer_segment() {
        return customer_segment;
    }

    public void setCustomer_segment(String[] customer_segment) {
        this.customer_segment = customer_segment;
    }
}