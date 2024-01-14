package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.UserProfileRequest;
import com.group8.projectpfe.domain.dto.UserProfileResponse;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.exception.ResourceNotFound;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.Impl.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/profile")
public class profileController {


    private final ImageService imageService;
    private final UserRepository userRepository;
    private final ProfileService profileService;
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getProfile(@PathVariable String fileName) throws IOException {
        Resource resource = imageService.getProfile(fileName);

        // Determine the content type dynamically based on the file/resource
        MediaType mediaType = MediaType.IMAGE_JPEG; // Set a default media type, change accordingly

        try {
            mediaType = MediaType.parseMediaType(imageService.getContentType(fileName));
        } catch (IOException e) {
            // Handle exception if content type cannot be determined
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
    @GetMapping("/profile")
    public UserProfileResponse getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(ResourceNotFound::new);

        UserProfileResponse response = new UserProfileResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());


        return response;
    }
    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileRequest userProfileRequest) {
        String email = userDetails.getUsername();

        // Update the user profile
        User updatedUser = profileService.updateUserProfile(email, userProfileRequest);


        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}

