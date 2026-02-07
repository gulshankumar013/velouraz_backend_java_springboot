package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "top_category_slider")
public class TopCategorySlider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slider_id")
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String sliderImage;   // Base64 image

    private String sliderTitle;


    @Column(columnDefinition = "TEXT")
    private String sliderDetails;
}
