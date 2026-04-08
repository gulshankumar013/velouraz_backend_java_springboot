package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.NewRingsArrivedRequest;
import com.Velouraz.velouraz.entity.NewRingsArrived;
import com.Velouraz.velouraz.repository.NewRingsArrivedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/new-rings")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class NewRingsArrivedController {

    @Autowired
    private NewRingsArrivedRepository repo;

    // 1️⃣ Upload Ring
    @PostMapping("/upload")
    public ResponseEntity<?> uploadRing(
            @RequestBody NewRingsArrivedRequest request
    ) {
        try {
            NewRingsArrived item = new NewRingsArrived();

            item.setRingTitle(request.getRing_title());
            item.setRingPrice(request.getRing_price());
            item.setRingDetails(request.getRing_details());

            item.setRingImage(request.getRing_image());
            item.setRingImage2(request.getRing_image2());
            item.setRingImage3(request.getRing_image3());

            NewRingsArrived saved = repo.save(item);

            List<String> images = List.of(
                    saved.getRingImage(),
                    saved.getRingImage2(),
                    saved.getRingImage3()
            ).stream().filter(img -> img != null).toList();

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Ring uploaded successfully",
                            "data", Map.of(
                                    "id", saved.getId(),
                                    "ring_title", saved.getRingTitle(),
                                    "ring_price", saved.getRingPrice(),
                                    "ring_details", saved.getRingDetails(),
                                    "images", images
                            )
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }

    // 2️⃣ Get All Rings
    @GetMapping("/all")
    public ResponseEntity<?> getAllRings() {

        List<NewRingsArrived> items = repo.findAll();

        List<?> response = items.stream().map(r -> {

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

    // 3️⃣ Get Single Ring
    @GetMapping("/{id}")
    public ResponseEntity<?> getRing(@PathVariable Long id) {

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

    // 4️⃣ Update Ring
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRing(
            @PathVariable Long id,
            @RequestBody NewRingsArrivedRequest request
    ) {
        return repo.findById(id)
                .map(existing -> {

                    existing.setRingTitle(request.getRing_title());
                    existing.setRingPrice(request.getRing_price());
                    existing.setRingDetails(request.getRing_details());

                    if (request.getRing_image() != null) {
                        existing.setRingImage(request.getRing_image());
                    }
                    if (request.getRing_image2() != null) {
                        existing.setRingImage2(request.getRing_image2());
                    }
                    if (request.getRing_image3() != null) {
                        existing.setRingImage3(request.getRing_image3());
                    }

                    NewRingsArrived updated = repo.save(existing);

                    return ResponseEntity.ok(
                            Map.of(
                                    "message", "Ring updated successfully",
                                    "data", Map.of(
                                            "id", updated.getId(),
                                            "ring_title", updated.getRingTitle(),
                                            "ring_price", updated.getRingPrice(),
                                            "ring_details", updated.getRingDetails(),
                                            "images", List.of(
                                                    updated.getRingImage(),
                                                    updated.getRingImage2(),
                                                    updated.getRingImage3()
                                            ).stream().filter(i -> i != null).toList()
                                    )
                            )
                    );
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "Ring not found")));
    }

    // 5️⃣ Delete Ring
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRing(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Ring not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Ring deleted successfully!");
    }
}
