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
@CrossOrigin(origins = "http://localhost:5173")
public class TopCategorySliderController {

    @Autowired
    private TopCategorySliderRepository repo;

    // 1️⃣ Upload Slider Product
    @PostMapping("/upload")
    public ResponseEntity<?> uploadSlider(@RequestBody TopCategorySliderRequest request) {
        try {
            TopCategorySlider slider = new TopCategorySlider();

            slider.setSliderTitle(request.getSlider_title());

            slider.setSliderDetails(request.getSlider_details());
            slider.setSliderImage(request.getSlider_image());

            TopCategorySlider saved = repo.save(slider);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Slider product uploaded successfully",
                            "id", saved.getId()
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Upload failed"));
        }
    }

    // 2️⃣ Get All Slider Products
    @GetMapping("/all")
    public ResponseEntity<?> getAllSliders() {

        List<?> response = repo.findAll().stream().map(s ->
                Map.of(
                        "id", s.getId(),
                        "slider_title", s.getSliderTitle(),

                        "slider_details", s.getSliderDetails(),
                        "slider_image", s.getSliderImage()
                )
        ).toList();

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
