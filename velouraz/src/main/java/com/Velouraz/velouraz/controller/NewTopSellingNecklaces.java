package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_top_selling_necklaces")
public class NewTopSellingNecklaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "neck_id")
    private Long id;

    @Lob
    @Column(name = "necklace_sets_image", columnDefinition = "LONGTEXT")
    private String image1;

    @Lob
    @Column(name = "necklace_sets_image2", columnDefinition = "LONGTEXT")
    private String image2;

    @Lob
    @Column(name = "necklace_sets_image3", columnDefinition = "LONGTEXT")
    private String image3;

    @Column(name = "necklace_sets_title")
    private String title;

    @Column(name = "necklace_sets_price")
    private Double price;

    @Column(name = "necklace_sets_details", columnDefinition = "TEXT")
    private String details;
}
