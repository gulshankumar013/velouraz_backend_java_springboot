package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_rings_arrived")
public class NewRingsArrived {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ring_id")
    private Long id;

    @Lob
    @Column(name = "ring_image", columnDefinition = "LONGTEXT")
    private String ringImage;

    @Lob
    @Column(name = "ring_image2", columnDefinition = "LONGTEXT")
    private String ringImage2;

    @Lob
    @Column(name = "ring_image3", columnDefinition = "LONGTEXT")
    private String ringImage3;

    @Column(name = "ring_title")
    private String ringTitle;

    @Column(name = "ring_price")
    private Double ringPrice;

    @Column(name = "ring_details", columnDefinition = "TEXT")
    private String ringDetails;
}
