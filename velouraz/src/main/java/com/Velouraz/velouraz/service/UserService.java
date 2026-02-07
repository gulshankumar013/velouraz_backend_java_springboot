package com.Velouraz.velouraz.service;

import com.Velouraz.velouraz.entity.User;
import com.Velouraz.velouraz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final String uploadDir = "uploads/profile-images/";

    public String saveProfileImage(Long userId, MultipartFile file) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = userId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.write(filePath, file.getBytes());

            String imageUrl = "/uploads/profile-images/" + fileName;

            user.setProfileImage(imageUrl);
            userRepository.save(user);

            return imageUrl;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }
}
