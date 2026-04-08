package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.JhumkaEarringRequest;
import com.Velouraz.velouraz.entity.JhumkaEarring;
import com.Velouraz.velouraz.repository.JhumkaEarringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/jhumka-earrings")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class JhumkaEarringController {

    @Autowired
    private JhumkaEarringRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody JhumkaEarringRequest request) {
        try {
            JhumkaEarring j = new JhumkaEarring();

            j.setTitle(request.getTitle());
            j.setPrice(request.getPrice());
            j.setDetails(request.getDetails());

            j.setImage(request.getImage());
            j.setImage2(request.getImage2());
            j.setImage3(request.getImage3());

            JhumkaEarring saved = repo.save(j);

            return ResponseEntity.ok(Map.of(
                    "message", "Jhumka earring uploaded successfully",
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

        List<JhumkaEarring> list = repo.findAll();

        List<?> response = list.stream().map(j -> {

            List<String> images = new ArrayList<>();
            if (j.getImage() != null) images.add(j.getImage());
            if (j.getImage2() != null) images.add(j.getImage2());
            if (j.getImage3() != null) images.add(j.getImage3());

            return Map.of(
                    "id", j.getId(),
                    "title", j.getTitle(),
                    "price", j.getPrice(),
                    "details", j.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(j -> {

                    List<String> images = new ArrayList<>();
                    if (j.getImage() != null) images.add(j.getImage());
                    if (j.getImage2() != null) images.add(j.getImage2());
                    if (j.getImage3() != null) images.add(j.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", j.getId(),
                            "title", j.getTitle(),
                            "price", j.getPrice(),
                            "details", j.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody JhumkaEarringRequest request
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

                    JhumkaEarring updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Jhumka earring updated successfully",
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
                        .body(Map.of("message", "Jhumka earring not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Jhumka earring not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Jhumka earring deleted successfully!");
    }
}