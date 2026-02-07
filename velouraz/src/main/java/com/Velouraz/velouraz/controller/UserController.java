package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.entity.User;
import com.Velouraz.velouraz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    private final String uploadDir = "uploads/profile-images/";
    // GET ALL USERS
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userRepo.findById(id)
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .body("User not found with ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRepo.findById(id)
                .map(user -> {
                    userRepo.delete(user);
                    return ResponseEntity.ok("User deleted successfully with ID: " + id);
                })
                .orElse(ResponseEntity.badRequest().body("User not found with ID: " + id));

    }

    @PutMapping("/{id}/profile-image")
    public ResponseEntity<?> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) {

        try {
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            byte[] bytes = image.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(bytes);

            user.setProfileImage(base64Image);
            userRepo.save(user);

            Map<String, String> response = new HashMap<>();
            response.put("profileImage", base64Image);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Image upload failed");
        }
    }

}
