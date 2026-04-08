package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.KundanJewelleryRequest;
import com.Velouraz.velouraz.entity.KundanJewellery;
import com.Velouraz.velouraz.repository.KundanJewelleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/kundan-jewellery")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class KundanJewelleryController {

    @Autowired
    private KundanJewelleryRepository repo;

    // 1️⃣ Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody KundanJewelleryRequest request) {
        try {
            KundanJewellery k = new KundanJewellery();

            k.setTitle(request.getTitle());
            k.setPrice(request.getPrice());
            k.setDetails(request.getDetails());

            k.setImage(request.getImage());
            k.setImage2(request.getImage2());
            k.setImage3(request.getImage3());

            KundanJewellery saved = repo.save(k);

            return ResponseEntity.ok(Map.of(
                    "message", "Kundan jewellery uploaded successfully",
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

        List<KundanJewellery> list = repo.findAll();

        List<?> response = list.stream().map(k -> {

            List<String> images = new ArrayList<>();
            if (k.getImage() != null) images.add(k.getImage());
            if (k.getImage2() != null) images.add(k.getImage2());
            if (k.getImage3() != null) images.add(k.getImage3());

            return Map.of(
                    "id", k.getId(),
                    "title", k.getTitle(),
                    "price", k.getPrice(),
                    "details", k.getDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {

        return repo.findById(id)
                .map(k -> {

                    List<String> images = new ArrayList<>();
                    if (k.getImage() != null) images.add(k.getImage());
                    if (k.getImage2() != null) images.add(k.getImage2());
                    if (k.getImage3() != null) images.add(k.getImage3());

                    return ResponseEntity.ok(Map.of(
                            "id", k.getId(),
                            "title", k.getTitle(),
                            "price", k.getPrice(),
                            "details", k.getDetails(),
                            "images", images
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody KundanJewelleryRequest request
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

                    KundanJewellery updated = repo.save(existing);

                    return ResponseEntity.ok(Map.of(
                            "message", "Kundan jewellery updated successfully",
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
                        .body(Map.of("message", "Kundan jewellery not found")));
    }

    // 5️⃣ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Kundan jewellery not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Kundan jewellery deleted successfully!");
    }
}