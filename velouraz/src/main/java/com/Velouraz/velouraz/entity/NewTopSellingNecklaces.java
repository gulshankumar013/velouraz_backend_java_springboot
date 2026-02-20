package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String necklaceSetsImage;

    @Lob
    @Column(name = "necklace_sets_image2", columnDefinition = "LONGTEXT")
    private String necklaceSetsImage2;

    @Lob
    @Column(name = "necklace_sets_image3", columnDefinition = "LONGTEXT")
    private String necklaceSetsImage3;

    @Column(name = "necklace_sets_title")
    private String necklaceSetsTitle;

    @Column(name = "necklace_sets_price")
    private Double necklaceSetsPrice;

    @Column(name = "necklace_sets_details", columnDefinition = "TEXT")
    private String necklaceSetsDetails;
}
