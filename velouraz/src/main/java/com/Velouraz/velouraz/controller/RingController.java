package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.RingRequest;
import com.Velouraz.velouraz.entity.Ring;
import com.Velouraz.velouraz.repository.RingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rings")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class RingController {

    @Autowired
    private RingRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody RingRequest request) {
        try {
            Ring r = new Ring();

            r.setTitle(request.getTitle());
            r.setPrice(request.getPrice());
            r.setDetails(request.getDetails());

            r.setImage(request.getImage());
            r.setImage2(request.getImage2());
            r.setImage3(request.getImage3());

            Ring saved = repo.save(r);

            return ResponseEntity.ok(Map.of(
                    "message", "Ring uploaded successfully",
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

        List<Ring> list = repo.findAll();

        List<?> response = list.stream().map(r -> {

            List<String> images = new ArrayList<>();
            if (r.getImage() != null) images.add(r.getImage());
            if (r.getImage2() != null) images.add(r.getImage2());
            if (r.getImage3() != null) images.add(r.getImage3());

            return Map.of(
                    "id", r.getId(),
                    "title", r.getTitle(),
                    "price", r.getPrice(),
                    "details", r.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(r -> {

                    List<String> images = new ArrayList<>();
                    if (r.getImage() != null) images.add(r.getImage());
                    if (r.getImage2() != null) images.add(r.getImage2());
                    if (r.getImage3() != null) images.add(r.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", r.getId(),
                            "title", r.getTitle(),
                            "price", r.getPrice(),
                            "details", r.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody RingRequest request
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

                    Ring updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Ring updated successfully",
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
                        .body(Map.of("message", "Ring not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Ring not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Ring deleted successfully!");
    }
}