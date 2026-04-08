package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.PendantSetRequest;
import com.Velouraz.velouraz.entity.PendantSet;
import com.Velouraz.velouraz.repository.PendantSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/pendant-sets")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class PendantSetController {

    @Autowired
    private PendantSetRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody PendantSetRequest request) {
        try {
            PendantSet p = new PendantSet();

            p.setTitle(request.getTitle());
            p.setPrice(request.getPrice());
            p.setDetails(request.getDetails());

            p.setImage(request.getImage());
            p.setImage2(request.getImage2());
            p.setImage3(request.getImage3());

            PendantSet saved = repo.save(p);

            return ResponseEntity.ok(Map.of(
                    "message", "Pendant set uploaded successfully",
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

        List<PendantSet> list = repo.findAll();

        List<?> response = list.stream().map(p -> {

            List<String> images = new ArrayList<>();
            if (p.getImage() != null) images.add(p.getImage());
            if (p.getImage2() != null) images.add(p.getImage2());
            if (p.getImage3() != null) images.add(p.getImage3());

            return Map.of(
                    "id", p.getId(),
                    "title", p.getTitle(),
                    "price", p.getPrice(),
                    "details", p.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(p -> {

                    List<String> images = new ArrayList<>();
                    if (p.getImage() != null) images.add(p.getImage());
                    if (p.getImage2() != null) images.add(p.getImage2());
                    if (p.getImage3() != null) images.add(p.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", p.getId(),
                            "title", p.getTitle(),
                            "price", p.getPrice(),
                            "details", p.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody PendantSetRequest request
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

                    PendantSet updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Pendant set updated successfully",
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
                        .body(Map.of("message", "Pendant set not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Pendant set not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Pendant set deleted successfully!");
    }
}