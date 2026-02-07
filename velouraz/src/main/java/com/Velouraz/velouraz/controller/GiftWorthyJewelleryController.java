package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.GiftWorthyJewelleryRequest;
import com.Velouraz.velouraz.entity.GiftWorthyJewellery;
import com.Velouraz.velouraz.repository.GiftWorthyJewelleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gift-worthy")
@CrossOrigin(origins = "http://localhost:5173")
public class GiftWorthyJewelleryController {

    @Autowired
    private GiftWorthyJewelleryRepository repo;

    // 1️⃣ Upload Gift Worthy Jewellery
    @PostMapping("/upload")
    public ResponseEntity<?> uploadGiftWorthy(
            @RequestBody GiftWorthyJewelleryRequest request
    ) {
        try {
            GiftWorthyJewellery item = new GiftWorthyJewellery();

            item.setGiftTitle(request.getGift_title());
            item.setGiftPrice(request.getGift_price());
            item.setGiftDetails(request.getGift_details());

            item.setGiftImage(request.getGift_image());
            item.setGiftImage2(request.getGift_image2());
            item.setGiftImage3(request.getGift_image3());

            GiftWorthyJewellery saved = repo.save(item);

            // ✅ Prepare image list
            List<String> images = List.of(
                    saved.getGiftImage(),
                    saved.getGiftImage2(),
                    saved.getGiftImage3()
            ).stream().filter(img -> img != null).toList();

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Gift worthy jewellery uploaded successfully",
                            "data", Map.of(
                                    "id", saved.getId(),
                                    "gift_title", saved.getGiftTitle(),
                                    "gift_price", saved.getGiftPrice(),
                                    "gift_details", saved.getGiftDetails(),
                                    "images", images
                            )
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }


    // 2️⃣ Get All Gift Worthy Jewellery
    @GetMapping("/all")
    public ResponseEntity<?> getAllGiftWorthy() {

        List<GiftWorthyJewellery> items = repo.findAll();

        List<?> response = items.stream().map(g -> {

            List<String> images = new ArrayList<>();

            if (g.getGiftImage() != null) images.add(g.getGiftImage());
            if (g.getGiftImage2() != null) images.add(g.getGiftImage2());
            if (g.getGiftImage3() != null) images.add(g.getGiftImage3());

            return Map.of(
                    "id", g.getId(),
                    "gift_title", g.getGiftTitle(),
                    "gift_price", g.getGiftPrice(),
                    "gift_details", g.getGiftDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single Gift Worthy Jewellery
    @GetMapping("/{id}")
    public ResponseEntity<?> getGiftWorthy(@PathVariable Long id) {

        return repo.findById(id)
                .map(g -> {

                    List<String> images = new ArrayList<>();

                    if (g.getGiftImage() != null) images.add(g.getGiftImage());
                    if (g.getGiftImage2() != null) images.add(g.getGiftImage2());
                    if (g.getGiftImage3() != null) images.add(g.getGiftImage3());

                    return ResponseEntity.ok(
                            Map.of(
                                    "id", g.getId(),
                                    "gift_title", g.getGiftTitle(),
                                    "gift_price", g.getGiftPrice(),
                                    "gift_details", g.getGiftDetails(),
                                    "images", images
                            )
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update Gift Worthy Jewellery
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGiftWorthy(
            @PathVariable Long id,
            @RequestBody GiftWorthyJewelleryRequest request
    ) {
        return repo.findById(id)
                .map(existing -> {

                    existing.setGiftTitle(request.getGift_title());
                    existing.setGiftPrice(request.getGift_price());
                    existing.setGiftDetails(request.getGift_details());

                    if (request.getGift_image() != null) {
                        existing.setGiftImage(request.getGift_image());
                    }
                    if (request.getGift_image2() != null) {
                        existing.setGiftImage2(request.getGift_image2());
                    }
                    if (request.getGift_image3() != null) {
                        existing.setGiftImage3(request.getGift_image3());
                    }

                    GiftWorthyJewellery updated = repo.save(existing);

                    return ResponseEntity.ok(
                            Map.of(
                                    "message", "Gift worthy jewellery updated successfully",
                                    "data", Map.of(
                                            "id", updated.getId(),
                                            "gift_title", updated.getGiftTitle(),
                                            "gift_price", updated.getGiftPrice(),
                                            "gift_details", updated.getGiftDetails(),
                                            "images", List.of(
                                                    updated.getGiftImage(),
                                                    updated.getGiftImage2(),
                                                    updated.getGiftImage3()
                                            ).stream().filter(i -> i != null).toList()
                                    )
                            )
                    );
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "Gift worthy jewellery not found")));
    }

    // 5️⃣ Delete Gift Worthy Jewellery
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGiftWorthy(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Gift worthy jewellery not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Gift worthy jewellery deleted successfully!");
    }
}
