package com.Velouraz.velouraz.dto;

public class CarouselSliderDTO {

    private Long id;
    private String title;
    private String description;
    private String imageBase64;

    public CarouselSliderDTO(Long id, String title, String description, String imageBase64) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageBase64 = imageBase64;
    }

    // GETTERS & SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}