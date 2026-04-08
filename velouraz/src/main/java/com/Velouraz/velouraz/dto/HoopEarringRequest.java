package com.Velouraz.velouraz.dto;

public class HoopEarringRequest {

    private String title;
    private Double price;
    private String details;

    private String image;
    private String image2;
    private String image3;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getImage2() { return image2; }
    public void setImage2(String image2) { this.image2 = image2; }

    public String getImage3() { return image3; }
    public void setImage3(String image3) { this.image3 = image3; }
}