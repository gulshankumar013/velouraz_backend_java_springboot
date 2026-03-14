package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.BangleSetRequest;
import com.Velouraz.velouraz.entity.BangleSet;
import com.Velouraz.velouraz.repository.BangleSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/bangles")
@CrossOrigin(origins = "http://localhost:5173")
public class BangleSetController {

    @Autowired
    private BangleSetRepository repo;

    // 1️⃣ Upload Bangle Set
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody BangleSetRequest request) {
        try {
            BangleSet b = new BangleSet();

            b.setTitle(request.getTitle());
            b.setPrice(request.getPrice());
            b.setDetails(request.getDetails());

            b.setImage(request.getImage());
            b.setImage2(request.getImage2());
            b.setImage3(request.getImage3());

            BangleSet saved = repo.save(b);

            return ResponseEntity.ok(Map.of(
                    "message", "Bangle Set uploaded successfully",
                    "id", saved.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }

    // 2️⃣ Get All
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        List<BangleSet> list = repo.findAll();

        List<?> response = list.stream().map(b -> {

            List<String> images = new ArrayList<>();
            if (b.getImage() != null) images.add(b.getImage());
            if (b.getImage2() != null) images.add(b.getImage2());
            if (b.getImage3() != null) images.add(b.getImage3());

            return Map.of(
                    "id", b.getId(),
                    "title", b.getTitle(),
                    "price", b.getPrice(),
                    "details", b.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(b -> {

                    List<String> images = new ArrayList<>();
                    if (b.getImage() != null) images.add(b.getImage());
                    if (b.getImage2() != null) images.add(b.getImage2());
                    if (b.getImage3() != null) images.add(b.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", b.getId(),
                            "title", b.getTitle(),
                            "price", b.getPrice(),
                            "details", b.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody BangleSetRequest request
    ) {
        return repo.findById(id)
                .map(existing -> {

                    existing.setTitle(request.getTitle());
                    existing.setPrice(request.getPrice());
                    existing.setDetails(request.getDetails());

                    if (request.getImage() != null)
                        existing.setImage(request.getImage());

                    if (request.getImage2() != null)
                        existing.setImage2(request.getImage2());

                    if (request.getImage3() != null)
                        existing.setImage3(request.getImage3());

                    BangleSet updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Bangle Set updated successfully",
                            "data", Map.of(
                                    "id", updated.getId(),
                                    "title", updated.getTitle(),
                                    "price", updated.getPrice(),
                                    "details", updated.getDetails(),
                                    "images", List.of(
                                            updated.getImage(),
                                            updated.getImage2(),
                                            updated.getImage3()
                                    ).stream().filter(Objects::nonNull).toList()
                            )
                    ));
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "Bangle Set not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Bangle Set not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Bangle Set deleted successfully!");
    }
}