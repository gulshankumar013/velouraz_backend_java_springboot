package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "earrings")
public class Earrings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ear_id")
    private Long id;

    @Lob
    @Column(name = "earrings_image", columnDefinition = "LONGTEXT")
    private String earringsImage;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String earringsImage2;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String earringsImage3;

    private String earringsTitle;

    private Double earringsPrice;

    @Column(columnDefinition = "TEXT")
    private String earringsDetails;
}
