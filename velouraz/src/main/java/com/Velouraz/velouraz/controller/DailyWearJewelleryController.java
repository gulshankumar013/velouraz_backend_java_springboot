package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.DailyWearJewelleryRequest;
import com.Velouraz.velouraz.entity.DailyWearJewellery;
import com.Velouraz.velouraz.repository.DailyWearJewelleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/daily-wear")
@CrossOrigin(origins = "http://localhost:5173")
public class DailyWearJewelleryController {

    @Autowired
    private DailyWearJewelleryRepository repo;

    // 1️⃣ Upload Daily Wear Jewellery
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDailyWear(
            @RequestBody DailyWearJewelleryRequest request
    ) {
        try {
            DailyWearJewellery item = new DailyWearJewellery();

            item.setDailyTitle(request.getDaily_title());
            item.setDailyPrice(request.getDaily_price());
            item.setDailyDetails(request.getDaily_details());

            item.setDailyImage(request.getDaily_image());
            item.setDailyImage2(request.getDaily_image2());
            item.setDailyImage3(request.getDaily_image3());

            DailyWearJewellery saved = repo.save(item);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Daily wear jewellery uploaded successfully",
                            "id", saved.getId()
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }

    // 2️⃣ Get All Daily Wear Jewellery
    @GetMapping("/all")
    public ResponseEntity<?> getAllDailyWear() {

        List<DailyWearJewellery> items = repo.findAll();

        List<?> response = items.stream().map(d -> {

            List<String> images = new ArrayList<>();

            if (d.getDailyImage() != null) images.add(d.getDailyImage());
            if (d.getDailyImage2() != null) images.add(d.getDailyImage2());
            if (d.getDailyImage3() != null) images.add(d.getDailyImage3());

            return Map.of(
                    "id", d.getId(),
                    "daily_title", d.getDailyTitle(),
                    "daily_price", d.getDailyPrice(),
                    "daily_details", d.getDailyDetails(),
                    "images", images
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get Single Daily Wear Jewellery
    @GetMapping("/{id}")
    public ResponseEntity<?> getDailyWear(@PathVariable Long id) {

        return repo.findById(id)
                .map(d -> {

                    List<String> images = new ArrayList<>();

                    if (d.getDailyImage() != null) images.add(d.getDailyImage());
                    if (d.getDailyImage2() != null) images.add(d.getDailyImage2());
                    if (d.getDailyImage3() != null) images.add(d.getDailyImage3());

                    return ResponseEntity.ok(
                            Map.of(
                                    "id", d.getId(),
                                    "daily_title", d.getDailyTitle(),
                                    "daily_price", d.getDailyPrice(),
                                    "daily_details", d.getDailyDetails(),
                                    "images", images
                            )
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update Daily Wear Jewellery
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDailyWear(
            @PathVariable Long id,
            @RequestBody DailyWearJewelleryRequest request
    ) {
        return repo.findById(id)
                .map(existing -> {

                    existing.setDailyTitle(request.getDaily_title());
                    existing.setDailyPrice(request.getDaily_price());
                    existing.setDailyDetails(request.getDaily_details());

                    if (request.getDaily_image() != null) {
                        existing.setDailyImage(request.getDaily_image());
                    }
                    if (request.getDaily_image2() != null) {
                        existing.setDailyImage2(request.getDaily_image2());
                    }
                    if (request.getDaily_image3() != null) {
                        existing.setDailyImage3(request.getDaily_image3());
                    }

                    DailyWearJewellery updated = repo.save(existing);

                    return ResponseEntity.ok(
                            Map.of(
                                    "message", "Daily wear jewellery updated successfully",
                                    "data", Map.of(
                                            "id", updated.getId(),
                                            "daily_title", updated.getDailyTitle(),
                                            "daily_price", updated.getDailyPrice(),
                                            "daily_details", updated.getDailyDetails(),
                                            "images", List.of(
                                                    updated.getDailyImage(),
                                                    updated.getDailyImage2(),
                                                    updated.getDailyImage3()
                                            ).stream().filter(i -> i != null).toList()
                                    )
                            )
                    );
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "Daily wear jewellery not found")));
    }

    // 5️⃣ Delete Daily Wear Jewellery
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDailyWear(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Daily wear jewellery not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Daily wear jewellery deleted successfully!");
    }
}
