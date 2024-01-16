package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.UserProfileRequest;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.exception.ResourceNotFound;
import com.group8.projectpfe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User updateUserProfile(String email, UserProfileRequest userProfileRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(ResourceNotFound::new);

        user.setFirstName(userProfileRequest.getFirstName());
        user.setLastName(userProfileRequest.getLastName());
        user.setEmail(userProfileRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userProfileRequest.getPassword()));

        // Save the updated user
        return userRepository.save(user);
    }
}
