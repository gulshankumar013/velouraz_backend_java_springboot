package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.NewTopSellingNecklacesRequest;
import com.Velouraz.velouraz.entity.NewTopSellingNecklaces;
import com.Velouraz.velouraz.repository.NewTopSellingNecklacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/new-top-selling-necklaces")
@CrossOrigin(origins = "http://localhost:5173")
public class NewTopSellingNecklacesController {

    @Autowired
    private NewTopSellingNecklacesRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody NewTopSellingNecklacesRequest request) {
        try {
            NewTopSellingNecklaces item = new NewTopSellingNecklaces();

            item.setNecklaceSetsTitle(request.getNecklace_sets_title());
            item.setNecklaceSetsPrice(request.getNecklace_sets_price());
            item.setNecklaceSetsDetails(request.getNecklace_sets_details());

            item.setNecklaceSetsImage(request.getNecklace_sets_image());
            item.setNecklaceSetsImage2(request.getNecklace_sets_image2());
            item.setNecklaceSetsImage3(request.getNecklace_sets_image3());

            NewTopSellingNecklaces saved = repo.save(item);

            List<String> images = List.of(
                    saved.getNecklaceSetsImage(),
                    saved.getNecklaceSetsImage2(),
                    saved.getNecklaceSetsImage3()
            ).stream().filter(i -> i != null).toList();

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Necklace uploaded successfully",
                            "data", Map.of(
                                    "id", saved.getId(),
                                    "necklace_sets_title", saved.getNecklaceSetsTitle(),
                                    "necklace_sets_price", saved.getNecklaceSetsPrice(),
                                    "necklace_sets_details", saved.getNecklaceSetsDetails(),
                                    "images", images
                            )
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }

    // 2️⃣ Get All
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        List<NewTopSellingNecklaces> items = repo.findAll();

        List<?> response = items.stream().map(n -> {

            List<String> images = new ArrayList<>();

            if (n.getNecklaceSetsImage() != null) images.add(n.getNecklaceSetsImage());
            if (n.getNecklaceSetsImage2() != null) images.add(n.getNecklaceSetsImage2());
            if (n.getNecklaceSetsImage3() != null) images.add(n.getNecklaceSetsImage3());

            return Map.of(
                    "id", n.getId(),
                    "necklace_sets_title", n.getNecklaceSetsTitle(),
                    "necklace_sets_price", n.getNecklaceSetsPrice(),
                    "necklace_sets_details", n.getNecklaceSetsDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        return repo.findById(id)
                .map(n -> {

                    List<String> images = new ArrayList<>();

                    if (n.getNecklaceSetsImage() != null) images.add(n.getNecklaceSetsImage());
                    if (n.getNecklaceSetsImage2() != null) images.add(n.getNecklaceSetsImage2());
                    if (n.getNecklaceSetsImage3() != null) images.add(n.getNecklaceSetsImage3());

                    return ResponseEntity.ok(
                            Map.of(
                                    "id", n.getId(),
                                    "necklace_sets_title", n.getNecklaceSetsTitle(),
                                    "necklace_sets_price", n.getNecklaceSetsPrice(),
                                    "necklace_sets_details", n.getNecklaceSetsDetails(),
                                    "images", images
                            )
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody NewTopSellingNecklacesRequest request) {

        return repo.findById(id)
                .map(existing -> {

                    existing.setNecklaceSetsTitle(request.getNecklace_sets_title());
                    existing.setNecklaceSetsPrice(request.getNecklace_sets_price());
                    existing.setNecklaceSetsDetails(request.getNecklace_sets_details());

                    if (request.getNecklace_sets_image() != null)
                        existing.setNecklaceSetsImage(request.getNecklace_sets_image());

                    if (request.getNecklace_sets_image2() != null)
                        existing.setNecklaceSetsImage2(request.getNecklace_sets_image2());

                    if (request.getNecklace_sets_image3() != null)
                        existing.setNecklaceSetsImage3(request.getNecklace_sets_image3());

                    NewTopSellingNecklaces updated = repo.save(existing);

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
                                            ).stream().filter(i -> i != null).toList()
                                    )
                            )
                    );
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "Necklace not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Necklace not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Necklace deleted successfully!");
    }
}
