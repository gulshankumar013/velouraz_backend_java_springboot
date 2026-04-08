package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.ChokerSetRequest;
import com.Velouraz.velouraz.entity.ChokerSet;
import com.Velouraz.velouraz.repository.ChokerSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/choker-sets")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class ChokerSetController {

    @Autowired
    private ChokerSetRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody ChokerSetRequest request) {
        try {
            ChokerSet c = new ChokerSet();

            c.setTitle(request.getTitle());
            c.setPrice(request.getPrice());
            c.setDetails(request.getDetails());

            c.setImage(request.getImage());
            c.setImage2(request.getImage2());
            c.setImage3(request.getImage3());

            ChokerSet saved = repo.save(c);

            return ResponseEntity.ok(Map.of(
                    "message", "Choker set uploaded successfully",
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

        List<ChokerSet> list = repo.findAll();

        List<?> response = list.stream().map(c -> {

            List<String> images = new ArrayList<>();
            if (c.getImage() != null) images.add(c.getImage());
            if (c.getImage2() != null) images.add(c.getImage2());
            if (c.getImage3() != null) images.add(c.getImage3());

            return Map.of(
                    "id", c.getId(),
                    "title", c.getTitle(),
                    "price", c.getPrice(),
                    "details", c.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(c -> {

                    List<String> images = new ArrayList<>();
                    if (c.getImage() != null) images.add(c.getImage());
                    if (c.getImage2() != null) images.add(c.getImage2());
                    if (c.getImage3() != null) images.add(c.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", c.getId(),
                            "title", c.getTitle(),
                            "price", c.getPrice(),
                            "details", c.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody ChokerSetRequest request
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

                    ChokerSet updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Choker set updated successfully",
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
                        .body(Map.of("message", "Choker set not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Choker set not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Choker set deleted successfully!");
    }
}