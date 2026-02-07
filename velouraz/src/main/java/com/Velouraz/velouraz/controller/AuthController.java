package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.dto.LoginRequest;
import com.Velouraz.velouraz.dto.SignupRequest;
import com.Velouraz.velouraz.entity.User;
import com.Velouraz.velouraz.repository.UserRepository;
import com.Velouraz.velouraz.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User u = new User();
        u.setFullName(request.getFullName());
        u.setEmail(request.getEmail());
        u.setMobile(request.getMobile());
        u.setPassword(encoder.encode(request.getPassword()));
        u.setRole(request.getRole());

        userRepo.save(u);

        return ResponseEntity.ok("Account created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 1. Check if user exists
        User user = userRepo.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found with this email");
        }

        // 2. Validate password
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        // 3. Generate JWT Token
        String token = jwtUtil.generateToken(user.getEmail());

        // 4. Return Token + User Info
        return ResponseEntity.ok(Map.of(
                "token", token,
                "fullName", user.getFullName(),
                "email", user.getEmail(),
                "role", user.getRole(),
                "id",user.getId()
        ));
    }

}
