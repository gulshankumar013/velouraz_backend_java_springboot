package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_daily_wear_jewellery")
public class DailyWearJewellery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long id;

    @Lob
    @Column(name = "daily_image", columnDefinition = "LONGTEXT")
    private String dailyImage;

    @Lob
    @Column(name = "daily_image2", columnDefinition = "LONGTEXT")
    private String dailyImage2;

    @Lob
    @Column(name = "daily_image3", columnDefinition = "LONGTEXT")
    private String dailyImage3;

    @Column(name = "daily_title")
    private String dailyTitle;

    @Column(name = "daily_price")
    private Double dailyPrice;

    @Column(name = "daily_details", columnDefinition = "TEXT")
    private String dailyDetails;
}
