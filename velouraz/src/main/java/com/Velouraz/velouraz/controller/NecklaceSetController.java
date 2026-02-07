package com.Velouraz.velouraz.controller;


import com.Velouraz.velouraz.dto.NecklaceSetRequest;
import com.Velouraz.velouraz.entity.NecklaceSet;
import com.Velouraz.velouraz.repository.NecklaceSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/necklace")
@CrossOrigin(origins = "http://localhost:5173")
public class NecklaceSetController {

    @Autowired
    private NecklaceSetRepository repo;

    // 1️⃣ Upload Necklace Set
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNecklaceSet(
            @RequestBody NecklaceSetRequest request
    ) {
        try {
            NecklaceSet set = new NecklaceSet();

            set.setNecklaceSetsTitle(request.getNecklace_sets_title());
            set.setNecklaceSetsPrice(request.getNecklace_sets_price());
            set.setNecklaceSetsDetails(request.getNecklace_sets_details());

            set.setNecklaceSetsImage(request.getNecklace_sets_image());
            set.setNecklaceSetsImage2(request.getNecklace_sets_image2());
            set.setNecklaceSetsImage3(request.getNecklace_sets_image3());

            NecklaceSet savedSet = repo.save(set);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Necklace uploaded successfully",
                            "id", savedSet.getId()
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }


    // 2️⃣ Get All Necklace Sets
    @GetMapping("/all")
    public ResponseEntity<?> getAllNecklaceSets() {

        List<NecklaceSet> sets = repo.findAll();

        List<?> response = sets.stream().map(s -> {

            List<String> images = new ArrayList<>();

            if (s.getNecklaceSetsImage() != null) {
                images.add(s.getNecklaceSetsImage());
            }
            if (s.getNecklaceSetsImage2() != null) {
                images.add(s.getNecklaceSetsImage2());
            }
            if (s.getNecklaceSetsImage3() != null) {
                images.add(s.getNecklaceSetsImage3());
            }

            return Map.of(
                    "id", s.getId(),
                    "necklace_sets_title", s.getNecklaceSetsTitle(),
                    "necklace_sets_price", s.getNecklaceSetsPrice(),
                    "necklace_sets_details", s.getNecklaceSetsDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }



    // 3️⃣ Get Single Necklace Set
    @GetMapping("/{id}")
    public ResponseEntity<?> getNecklaceSet(@PathVariable Long id) {

        return repo.findById(id)
                .map(s -> {

                    List<String> images = new ArrayList<>();

                    if (s.getNecklaceSetsImage() != null) {
                        images.add(s.getNecklaceSetsImage());
                    }
                    if (s.getNecklaceSetsImage2() != null) {
                        images.add(s.getNecklaceSetsImage2());
                    }
                    if (s.getNecklaceSetsImage3() != null) {
                        images.add(s.getNecklaceSetsImage3());
                    }

                    return ResponseEntity.ok(
                            Map.of(
                                    "id", s.getId(),
                                    "necklace_sets_title", s.getNecklaceSetsTitle(),
                                    "necklace_sets_price", s.getNecklaceSetsPrice(),
                                    "necklace_sets_details", s.getNecklaceSetsDetails(),
                                    "images", images
                            )
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // 5️⃣ Update Necklace Set
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNecklaceSet(
            @PathVariable Long id,
            @RequestBody NecklaceSetRequest request
    ) {
        return repo.findById(id)
                .map(existing -> {

                    // Basic fields
                    existing.setNecklaceSetsTitle(request.getNecklace_sets_title());
                    existing.setNecklaceSetsPrice(request.getNecklace_sets_price());
                    existing.setNecklaceSetsDetails(request.getNecklace_sets_details());

                    // 🔥 UPDATE IMAGES (only if provided)
                    if (request.getNecklace_sets_image() != null) {
                        existing.setNecklaceSetsImage(request.getNecklace_sets_image());
                    }

                    if (request.getNecklace_sets_image2() != null) {
                        existing.setNecklaceSetsImage2(request.getNecklace_sets_image2());
                    }

                    if (request.getNecklace_sets_image3() != null) {
                        existing.setNecklaceSetsImage3(request.getNecklace_sets_image3());
                    }

                    NecklaceSet updated = repo.save(existing);

                    return ResponseEntity.ok(
                            Map.of(
                                    "message", "Necklace updated successfully",
                                    "data", Map.of(
                                            "id", updated.getId(),
                                            "necklace_sets_title", updated.getNecklaceSetsTitle(),
                                            "necklace_sets_price", updated.getNecklaceSetsPrice(),
                                            "necklace_sets_details", updated.getNecklaceSetsDetails(),
                                            "images", List.of(
                                                    updated.getNecklaceSetsImage(),
                                                    updated.getNecklaceSetsImage2(),
                                                    updated.getNecklaceSetsImage3()
                                            ).stream().filter(img -> img != null).toList()
                                    )
                            )
                    );
                })
                .orElseGet(() ->
                        ResponseEntity.badRequest().body(
                                Map.of("message", "Necklace set not found")
                        )
                );
    }



    // 4️⃣ Delete Necklace Set
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNecklaceSet(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest().body("Necklace set not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Necklace set deleted successfully!");
    }
}