package com.Velouraz.velouraz.dto;

public class NewRingsArrivedCatRequest {

    private String ring_title;
    private Double ring_price;
    private String ring_details;

    private String ring_image;
    private String ring_image2;
    private String ring_image3;

    public String getRing_title() {
        return ring_title;
    }

    public void setRing_title(String ring_title) {
        this.ring_title = ring_title;
    }

    public Double getRing_price() {
        return ring_price;
    }

    public void setRing_price(Double ring_price) {
        this.ring_price = ring_price;
    }

    public String getRing_details() {
        return ring_details;
    }

    public void setRing_details(String ring_details) {
        this.ring_details = ring_details;
    }

    public String getRing_image() {
        return ring_image;
    }

    public void setRing_image(String ring_image) {
        this.ring_image = ring_image;
    }

    public String getRing_image2() {
        return ring_image2;
    }

    public String getRing_image3() {
        return ring_image3;
    }
}
