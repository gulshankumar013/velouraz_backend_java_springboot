package com.Velouraz.velouraz.dto;

public class GiftWorthyJewelleryRequest {

    private String gift_title;
    private Double gift_price;
    private String gift_details;

    private String gift_image;
    private String gift_image2;
    private String gift_image3;

    public String getGift_title() {
        return gift_title;
    }

    public void setGift_title(String gift_title) {
        this.gift_title = gift_title;
    }

    public Double getGift_price() {
        return gift_price;
    }

    public void setGift_price(Double gift_price) {
        this.gift_price = gift_price;
    }

    public String getGift_details() {
        return gift_details;
    }

    public void setGift_details(String gift_details) {
        this.gift_details = gift_details;
    }

    public String getGift_image() {
        return gift_image;
    }

    public void setGift_image(String gift_image) {
        this.gift_image = gift_image;
    }

    public String getGift_image2() {
        return gift_image2;
    }

    public String getGift_image3() {
        return gift_image3;
    }
}
