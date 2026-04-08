package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.HoopEarringRequest;
import com.Velouraz.velouraz.entity.HoopEarring;
import com.Velouraz.velouraz.repository.HoopEarringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/hoop-earrings")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class HoopEarringController {

    @Autowired
    private HoopEarringRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody HoopEarringRequest request) {
        try {
            HoopEarring h = new HoopEarring();

            h.setTitle(request.getTitle());
            h.setPrice(request.getPrice());
            h.setDetails(request.getDetails());

            h.setImage(request.getImage());
            h.setImage2(request.getImage2());
            h.setImage3(request.getImage3());

            HoopEarring saved = repo.save(h);

            return ResponseEntity.ok(Map.of(
                    "message", "Hoop earring uploaded successfully",
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

        List<HoopEarring> list = repo.findAll();

        List<?> response = list.stream().map(h -> {

            List<String> images = new ArrayList<>();
            if (h.getImage() != null) images.add(h.getImage());
            if (h.getImage2() != null) images.add(h.getImage2());
            if (h.getImage3() != null) images.add(h.getImage3());

            return Map.of(
                    "id", h.getId(),
                    "title", h.getTitle(),
                    "price", h.getPrice(),
                    "details", h.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(h -> {

                    List<String> images = new ArrayList<>();
                    if (h.getImage() != null) images.add(h.getImage());
                    if (h.getImage2() != null) images.add(h.getImage2());
                    if (h.getImage3() != null) images.add(h.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", h.getId(),
                            "title", h.getTitle(),
                            "price", h.getPrice(),
                            "details", h.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody HoopEarringRequest request
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

                    HoopEarring updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Hoop earring updated successfully",
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
                        .body(Map.of("message", "Hoop earring not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Hoop earring not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Hoop earring deleted successfully!");
    }
}