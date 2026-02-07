package com.Velouraz.velouraz.dto;

public class DailyWearJewelleryRequest {

    private String daily_title;
    private Double daily_price;
    private String daily_details;

    private String daily_image;
    private String daily_image2;
    private String daily_image3;

    public String getDaily_title() {
        return daily_title;
    }

    public void setDaily_title(String daily_title) {
        this.daily_title = daily_title;
    }

    public Double getDaily_price() {
        return daily_price;
    }

    public void setDaily_price(Double daily_price) {
        this.daily_price = daily_price;
    }

    public String getDaily_details() {
        return daily_details;
    }

    public void setDaily_details(String daily_details) {
        this.daily_details = daily_details;
    }

    public String getDaily_image() {
        return daily_image;
    }

    public void setDaily_image(String daily_image) {
        this.daily_image = daily_image;
    }

    public String getDaily_image2() {
        return daily_image2;
    }

    public String getDaily_image3() {
        return daily_image3;
    }
}
