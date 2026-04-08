package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "anklets")
public class Anklet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double price;
    private String details;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image2;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image3;
}