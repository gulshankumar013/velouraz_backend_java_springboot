package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.AnkletRequest;
import com.Velouraz.velouraz.entity.Anklet;
import com.Velouraz.velouraz.repository.AnkletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/anklets")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class AnkletController {

    @Autowired
    private AnkletRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody AnkletRequest request) {
        try {
            Anklet a = new Anklet();

            a.setTitle(request.getTitle());
            a.setPrice(request.getPrice());
            a.setDetails(request.getDetails());

            a.setImage(request.getImage());
            a.setImage2(request.getImage2());
            a.setImage3(request.getImage3());

            Anklet saved = repo.save(a);

            return ResponseEntity.ok(Map.of(
                    "message", "Anklet uploaded successfully",
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

        List<Anklet> list = repo.findAll();

        List<?> response = list.stream().map(a -> {

            List<String> images = new ArrayList<>();
            if (a.getImage() != null) images.add(a.getImage());
            if (a.getImage2() != null) images.add(a.getImage2());
            if (a.getImage3() != null) images.add(a.getImage3());

            return Map.of(
                    "id", a.getId(),
                    "title", a.getTitle(),
                    "price", a.getPrice(),
                    "details", a.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(a -> {

                    List<String> images = new ArrayList<>();
                    if (a.getImage() != null) images.add(a.getImage());
                    if (a.getImage2() != null) images.add(a.getImage2());
                    if (a.getImage3() != null) images.add(a.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", a.getId(),
                            "title", a.getTitle(),
                            "price", a.getPrice(),
                            "details", a.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody AnkletRequest request
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

                    Anklet updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Anklet updated successfully",
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
                        .body(Map.of("message", "Anklet not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Anklet not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Anklet deleted successfully!");
    }
}