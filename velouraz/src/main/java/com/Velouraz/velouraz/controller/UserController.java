package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.entity.User;
import com.Velouraz.velouraz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepo;

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
}