package com.Velouraz.velouraz.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "necklace_sets")
public class NecklaceSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "neck_id")
    private Long id;

    @Lob
    @Column(name = "necklace_sets_image", columnDefinition = "LONGTEXT")
    private String necklaceSetsImage;// Base64 string

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String necklaceSetsImage2;  // image 2

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String necklaceSetsImage3;  // image 3

    private String necklaceSetsTitle;

    private Double necklaceSetsPrice;

    @Column(columnDefinition = "TEXT")
    private String necklaceSetsDetails;
}