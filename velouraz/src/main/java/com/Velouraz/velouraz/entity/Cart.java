package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcart")
    private Long idcart;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "itemid")
    private Long itemid;

    @Lob
    @Column(name = "item_image")
    private String itemImage;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_details")
    private String itemDetails;

    @Column(name = "item_price")
    private Double itemPrice;

    @Column(name = "item_color")
    private String itemColor;

    @Column(name = "item_size")
    private Integer itemSize;

    @Column(name = "quantity")
    private Integer quantity;


}

