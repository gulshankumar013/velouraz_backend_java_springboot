package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.EarringsRequest;
import com.Velouraz.velouraz.entity.Earrings;
import com.Velouraz.velouraz.repository.EarringsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/earrings")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class EarringsController {

    @Autowired
    private EarringsRepository repo;

    // 1️⃣ Upload Earrings
    @PostMapping("/upload")
    public ResponseEntity<?> uploadEarrings(@RequestBody EarringsRequest request) {
        try {
            Earrings e = new Earrings();

            e.setEarringsTitle(request.getEarrings_title());
            e.setEarringsPrice(request.getEarrings_price());
            e.setEarringsDetails(request.getEarrings_details());

            e.setEarringsImage(request.getEarrings_image());
            e.setEarringsImage2(request.getEarrings_image2());
            e.setEarringsImage3(request.getEarrings_image3());

            Earrings saved = repo.save(e);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Earrings uploaded successfully",
                            "id", saved.getId()
                    )
            );

        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }

    // 2️⃣ Get All Earrings
    @GetMapping("/all")
    public ResponseEntity<?> getAllEarrings() {

        List<Earrings> list = repo.findAll();

        List<?> response = list.stream().map(e -> {

            List<String> images = new ArrayList<>();

            if (e.getEarringsImage() != null) images.add(e.getEarringsImage());
            if (e.getEarringsImage2() != null) images.add(e.getEarringsImage2());
            if (e.getEarringsImage3() != null) images.add(e.getEarringsImage3());

            return Map.of(
                    "id", e.getId(),
                    "earrings_title", e.getEarringsTitle(),
                    "earrings_price", e.getEarringsPrice(),
                    "earrings_details", e.getEarringsDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single Earrings
    @GetMapping("/{id}")
    public ResponseEntity<?> getEarrings(@PathVariable Long id) {

        return repo.findById(id)
                .map(e -> {

                    List<String> images = new ArrayList<>();

                    if (e.getEarringsImage() != null) images.add(e.getEarringsImage());
                    if (e.getEarringsImage2() != null) images.add(e.getEarringsImage2());
                    if (e.getEarringsImage3() != null) images.add(e.getEarringsImage3());

                    return ResponseEntity.ok(
                            Map.of(
                                    "id", e.getId(),
                                    "earrings_title", e.getEarringsTitle(),
                                    "earrings_price", e.getEarringsPrice(),
                                    "earrings_details", e.getEarringsDetails(),
                                    "images", images
                            )
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update Earrings
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEarrings(
            @PathVariable Long id,
            @RequestBody EarringsRequest request
    ) {

        return repo.findById(id)
                .map(existing -> {

                    existing.setEarringsTitle(request.getEarrings_title());
                    existing.setEarringsPrice(request.getEarrings_price());
                    existing.setEarringsDetails(request.getEarrings_details());

                    if (request.getEarrings_image() != null)
                        existing.setEarringsImage(request.getEarrings_image());

                    if (request.getEarrings_image2() != null)
                        existing.setEarringsImage2(request.getEarrings_image2());

                    if (request.getEarrings_image3() != null)
                        existing.setEarringsImage3(request.getEarrings_image3());

                    Earrings updated = repo.save(existing);

                    return ResponseEntity.ok(
                            Map.of(
                                    "message", "Earrings updated successfully",
                                    "data", Map.of(
                                            "id", updated.getId(),
                                            "earrings_title", updated.getEarringsTitle(),
                                            "earrings_price", updated.getEarringsPrice(),
                                            "earrings_details", updated.getEarringsDetails(),
                                            "images", List.of(
                                                    updated.getEarringsImage(),
                                                    updated.getEarringsImage2(),
                                                    updated.getEarringsImage3()
                                            ).stream().filter(Objects::nonNull).toList()
                                    )
                            )
                    );
                })
                .orElseGet(() ->
                        ResponseEntity.badRequest()
                                .body(Map.of("message", "Earrings not found"))
                );
    }

    // 5️⃣ Delete Earrings
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEarrings(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Earrings not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Earrings deleted successfully!");
    }
}
