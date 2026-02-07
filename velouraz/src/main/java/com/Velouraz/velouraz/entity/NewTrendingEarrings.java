package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_trending_earrings")
public class NewTrendingEarrings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earring_id")
    private Long id;

    @Lob
    @Column(name = "earring_image", columnDefinition = "LONGTEXT")
    private String earringImage;

    @Lob
    @Column(name = "earring_image2", columnDefinition = "LONGTEXT")
    private String earringImage2;

    @Lob
    @Column(name = "earring_image3", columnDefinition = "LONGTEXT")
    private String earringImage3;

    @Column(name = "earring_title")
    private String earringTitle;

    @Column(name = "earring_price")
    private Double earringPrice;

    @Column(name = "earring_details", columnDefinition = "TEXT")
    private String earringDetails;
}
