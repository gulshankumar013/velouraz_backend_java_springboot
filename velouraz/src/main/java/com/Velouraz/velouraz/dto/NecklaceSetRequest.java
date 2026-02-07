package com.Velouraz.velouraz.dto;

public class NecklaceSetRequest {

    private String necklace_sets_title;
    private Double necklace_sets_price;
    private String necklace_sets_details;
    private String necklace_sets_image;

    private String necklace_sets_image2;  // image 2
    private String necklace_sets_image3; // URL or path only

    public String getNecklace_sets_image() {
        return necklace_sets_image;
    }

    public void setNecklace_sets_image(String necklace_sets_image) {
        this.necklace_sets_image = necklace_sets_image;
    }

    public String getNecklace_sets_title() {
        return necklace_sets_title;
    }

    public void setNecklace_sets_title(String necklace_sets_title) {
        this.necklace_sets_title = necklace_sets_title;
    }

    public Double getNecklace_sets_price() {
        return necklace_sets_price;
    }

    public void setNecklace_sets_price(Double necklace_sets_price) {
        this.necklace_sets_price = necklace_sets_price;
    }

    public String getNecklace_sets_details() {
        return necklace_sets_details;
    }

    public void setNecklace_sets_details(String necklace_sets_details) {
        this.necklace_sets_details = necklace_sets_details;
    }

    public String getNecklace_sets_image2() {
        return necklace_sets_image2 ;
    }

    public String getNecklace_sets_image3() {
        return necklace_sets_image3;
    }
}