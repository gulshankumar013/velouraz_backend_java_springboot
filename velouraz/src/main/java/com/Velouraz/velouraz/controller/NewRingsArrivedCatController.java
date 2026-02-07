package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.NewRingsArrivedCatRequest;
import com.Velouraz.velouraz.entity.NewRingsArrivedCat;
import com.Velouraz.velouraz.repository.NewRingsArrivedCatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/new-rings-arrived")
@CrossOrigin(origins = "http://localhost:5173")
public class NewRingsArrivedCatController {

    @Autowired
    private NewRingsArrivedCatRepository repo;

    // 1️⃣ INSERT
    @PostMapping("/upload")
    public ResponseEntity<?> uploadRing(
            @RequestBody NewRingsArrivedCatRequest request
    ) {
        NewRingsArrivedCat ring = new NewRingsArrivedCat();

        ring.setRingTitle(request.getRing_title());
        ring.setRingPrice(request.getRing_price());
        ring.setRingDetails(request.getRing_details());

        ring.setRingImage(request.getRing_image());
        ring.setRingImage2(request.getRing_image2());
        ring.setRingImage3(request.getRing_image3());

        NewRingsArrivedCat saved = repo.save(ring);

        List<String> images = List.of(
                saved.getRingImage(),
                saved.getRingImage2(),
                saved.getRingImage3()
        ).stream().filter(i -> i != null).toList();

        return ResponseEntity.ok(
                Map.of(
                        "message", "New ring added successfully",
                        "data", Map.of(
                                "id", saved.getId(),
                                "ring_title", saved.getRingTitle(),
                                "ring_price", saved.getRingPrice(),
                                "ring_details", saved.getRingDetails(),
                                "images", images
                        )
                )
        );
    }

    // 2️⃣ GET ALL
    @GetMapping("/all")
    public ResponseEntity<?> getAllRings() {

        List<NewRingsArrivedCat> rings = repo.findAll();

        List<?> response = rings.stream().map(r -> {

            List<String> images = new ArrayList<>();
            if (r.getRingImage() != null) images.add(r.getRingImage());
            if (r.getRingImage2() != null) images.add(r.getRingImage2());
            if (r.getRingImage3() != null) images.add(r.getRingImage3());

            return Map.of(
                    "id", r.getId(),
                    "ring_title", r.getRingTitle(),
                    "ring_price", r.getRingPrice(),
                    "ring_details", r.getRingDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRingById(@PathVariable Long id) {

        return repo.findById(id)
                .map(r -> {

                    List<String> images = new ArrayList<>();
                    if (r.getRingImage() != null) images.add(r.getRingImage());
                    if (r.getRingImage2() != null) images.add(r.getRingImage2());
                    if (r.getRingImage3() != null) images.add(r.getRingImage3());

                    return ResponseEntity.ok(
                            Map.of(
                                    "id", r.getId(),
                                    "ring_title", r.getRingTitle(),
                                    "ring_price", r.getRingPrice(),
                                    "ring_details", r.getRingDetails(),
                                    "images", images
                            )
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRing(
            @PathVariable Long id,
            @RequestBody NewRingsArrivedCatRequest request
    ) {
        return repo.findById(id)
                .map(existing -> {

                    existing.setRingTitle(request.getRing_title());
                    existing.setRingPrice(request.getRing_price());
                    existing.setRingDetails(request.getRing_details());

                    if (request.getRing_image() != null)
                        existing.setRingImage(request.getRing_image());
                    if (request.getRing_image2() != null)
                        existing.setRingImage2(request.getRing_image2());
                    if (request.getRing_image3() != null)
                        existing.setRingImage3(request.getRing_image3());

                    repo.save(existing);

                    return ResponseEntity.ok(
                            Map.of("message", "Ring updated successfully")
                    );
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "Ring not found")));
    }

    // 5️⃣ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRing(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(
                Map.of("message", "Ring deleted successfully")
        );
    }
}
