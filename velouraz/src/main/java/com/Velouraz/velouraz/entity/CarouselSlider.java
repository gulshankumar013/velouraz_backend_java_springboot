package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CarouselSlider")
public class CarouselSlider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;      // optional – text for slider
    private String description; // optional

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;      // store image as BLOB

    // getters + setters
}
