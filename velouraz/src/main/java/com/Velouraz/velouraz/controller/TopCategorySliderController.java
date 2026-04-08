package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.TopCategorySliderRequest;
import com.Velouraz.velouraz.entity.TopCategorySlider;
import com.Velouraz.velouraz.repository.TopCategorySliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/top-category-slider")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class TopCategorySliderController {

    @Autowired
    private TopCategorySliderRepository repo;

    // 1️⃣ Upload Slider Product
    @PostMapping("/upload")
    public ResponseEntity<?> uploadSlider(@RequestBody TopCategorySliderRequest request) {
        try {

            // 🔐 Basic validation
            if (request.getSlider_title() == null || request.getSlider_title().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Slider title is required"));
            }

            if (request.getSlider_details() == null || request.getSlider_details().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Slider details are required"));
            }

            TopCategorySlider slider = new TopCategorySlider();
            slider.setSliderTitle(request.getSlider_title());
            slider.setSliderDetails(request.getSlider_details());

            // Image is optional
            if (request.getSlider_image() != null && !request.getSlider_image().isEmpty()) {
                slider.setSliderImage(request.getSlider_image());
            } else {
                slider.setSliderImage(null);
            }

            TopCategorySlider saved = repo.save(slider);

            // ✅ Safe response (NO Map.of with nulls)
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("message", "Top category slider uploaded successfully");
            response.put("data", Map.of(
                    "id", saved.getId(),
                    "slider_title", saved.getSliderTitle()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed due to server error"));
        }
    }


    // 2️⃣ Get All Slider Products
    @GetMapping("/all")
    public ResponseEntity<?> getAllSliders() {

        List<Map<String, Object>> response = repo.findAll().stream().map(s -> {
            Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", s.getId());
            map.put("slider_title", s.getSliderTitle());
            map.put("slider_details", s.getSliderDetails());
            map.put("slider_image", s.getSliderImage());
            return map;
        }).toList();

        return ResponseEntity.ok(response);
    }


    // 3️⃣ Get Single Slider Product
    @GetMapping("/{id}")
    public ResponseEntity<?> getSlider(@PathVariable Long id) {
        return repo.findById(id)
                .map(s -> ResponseEntity.ok(
                        Map.of(
                                "id", s.getId(),
                                "slider_title", s.getSliderTitle(),

                                "slider_details", s.getSliderDetails(),
                                "slider_image", s.getSliderImage()
                        )
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    // 4️⃣ Update Slider Product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlider(
            @PathVariable Long id,
            @RequestBody TopCategorySliderRequest request
    ) {
        return repo.findById(id)
                .map(existing -> {

                    existing.setSliderTitle(request.getSlider_title());

                    existing.setSliderDetails(request.getSlider_details());

                    if (request.getSlider_image() != null) {
                        existing.setSliderImage(request.getSlider_image());
                    }

                    TopCategorySlider updated = repo.save(existing);

                    return ResponseEntity.ok(
                            Map.of(
                                    "message", "Slider updated successfully",
                                    "data", Map.of(
                                            "id", updated.getId(),
                                            "slider_title", updated.getSliderTitle(),

                                            "slider_details", updated.getSliderDetails(),
                                            "slider_image", updated.getSliderImage()
                                    )
                            )
                    );
                })
                .orElse(ResponseEntity.badRequest()
                        .body(Map.of("message", "Slider not found")));
    }

    // 5️⃣ Delete Slider Product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlider(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Slider not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Slider deleted successfully!");
    }
}
