package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.NewTrendingEarringsRequest;
import com.Velouraz.velouraz.entity.NewTrendingEarrings;
import com.Velouraz.velouraz.repository.NewTrendingEarringsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/new-trending-earrings")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class NewTrendingEarringsController {

    @Autowired
    private NewTrendingEarringsRepository repo;

    // INSERT
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody NewTrendingEarringsRequest request) {

        NewTrendingEarrings e = new NewTrendingEarrings();
        e.setEarringTitle(request.getEarring_title());
        e.setEarringPrice(request.getEarring_price());
        e.setEarringDetails(request.getEarring_details());
        e.setEarringImage(request.getEarring_image());
        e.setEarringImage2(request.getEarring_image2());
        e.setEarringImage3(request.getEarring_image3());

        NewTrendingEarrings saved = repo.save(e);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Trending earring added successfully",
                        "data", Map.of(
                                "id", saved.getId(),
                                "earring_title", saved.getEarringTitle(),
                                "earring_price", saved.getEarringPrice(),
                                "earring_details", saved.getEarringDetails(),
                                "images", List.of(
                                        saved.getEarringImage(),
                                        saved.getEarringImage2(),
                                        saved.getEarringImage3()
                                ).stream().filter(i -> i != null).toList()
                        )
                )
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }
}
