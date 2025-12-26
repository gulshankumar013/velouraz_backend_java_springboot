package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.entity.CarouselSlider;
import com.Velouraz.velouraz.repository.CarouselSliderImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/carousel")
@CrossOrigin(origins = "http://localhost:3000")
public class CarouselSliderController {

    @Autowired
    private CarouselSliderImageRepository repo;

    // 1️⃣ Upload Slider Image
    @PostMapping("/upload")
    public ResponseEntity<?> uploadSliderImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description
    ) {
        try {
            CarouselSlider slider = new CarouselSlider();
            slider.setImage(file.getBytes());
            slider.setTitle(title);
            slider.setDescription(description);

            repo.save(slider);

            return ResponseEntity.ok("Slider image uploaded successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    // 2️⃣ Get All Slider Images
    @GetMapping("/all")
    public ResponseEntity<?> getAllSliders() {
        List<CarouselSlider> sliders = repo.findAll();

        // Convert images to Base64 for UI
        List<?> response = sliders.stream().map(s -> {
            String base64Image = Base64.getEncoder().encodeToString(s.getImage());
            return new Object() {
                public Long id = s.getId();
                public String title = s.getTitle();
                public String description = s.getDescription();
                public String image = base64Image;
            };
        }).toList();

        return ResponseEntity.ok(response);
    }

    // 3️⃣ Delete Slider Image
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlider(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.badRequest().body("Slider not found with ID: " + id);
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Slider deleted successfully!");
    }
}
