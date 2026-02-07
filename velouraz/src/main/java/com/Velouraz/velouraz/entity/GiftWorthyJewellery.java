package com.Velouraz.velouraz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_gift_worthy_jewellery")
public class GiftWorthyJewellery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_id")
    private Long id;

    @Lob
    @Column(name = "gift_image", columnDefinition = "LONGTEXT")
    private String giftImage;

    @Lob
    @Column(name = "gift_image2", columnDefinition = "LONGTEXT")
    private String giftImage2;

    @Lob
    @Column(name = "gift_image3", columnDefinition = "LONGTEXT")
    private String giftImage3;

    @Column(name = "gift_title")
    private String giftTitle;

    @Column(name = "gift_price")
    private Double giftPrice;

    @Column(name = "gift_details", columnDefinition = "TEXT")
    private String giftDetails;
}
