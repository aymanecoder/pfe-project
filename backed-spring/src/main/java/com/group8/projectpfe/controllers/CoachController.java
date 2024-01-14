package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.JwtService;
import com.group8.projectpfe.services.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/coachs")
public class CoachController {

    private final CoachService coachService;
    private final JwtService jwtService;
    private final ImageService imageService;
    @GetMapping("")
    public ResponseEntity<List<CoachDTO>> getCoachs(){
        List<CoachDTO> athletes = coachService.getCoachs();
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> getCoachById(@PathVariable Long id) {
        CoachDTO coachDTO = coachService.getCoachById(id);
        if (coachDTO != null) {
            return new ResponseEntity<>(coachDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User user =(User) authentication.getPrincipal(); // Retrieve the username (assuming it's the email)
            CoachDTO coachDTO = coachService.getCoachById(Long.valueOf(id));

            if (coachDTO != null && coachDTO.getId().equals(user.getId())) {
                try {
                    imageService.deleteProfile(coachDTO.getPicturePath());
                    coachService.deleteCoach(id, user.getId());
                } catch (IOException e) {
                    return new ResponseEntity<>("Failed to delete user or profile image", HttpStatus.INTERNAL_SERVER_ERROR);
                }

                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized delete or coach not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoach(@PathVariable Integer id, @RequestParam("file") MultipartFile file, @ModelAttribute CoachDTO coachDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal(); // Retrieve the authenticated user
            CoachDTO existingCoach = coachService.getCoachById(Long.valueOf(id));

            if (existingCoach != null && existingCoach.getId().equals(user.getId())) {
                // Check if the coach to be updated belongs to the authenticated user
                coachDTO.setId(id); // Set the ID in the DTO
                // Process the image file
                if (!file.isEmpty()) {
                    try {
                        String imagePath = imageService.saveImage(file); // Save the image and get the generated file path
                        if (existingCoach.getPicturePath() != null) {
                            imageService.deleteProfile(existingCoach.getPicturePath()); // Delete the old profile picture
                        }
                        coachDTO.setPicturePath(imagePath); // Set the image path in the DTO instead of the byte array
                    } catch (IOException e) {
                        // Handle file processing error
                        return new ResponseEntity<>("Failed to process the image", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                coachService.updateCoach(coachDTO);
                return new ResponseEntity<>("Coach updated successfully", HttpStatus.OK);
            } else {
                // Handle unauthorized update or coach not found
                return new ResponseEntity<>("Unauthorized update or coach not found", HttpStatus.NOT_FOUND);
            }
        } else {
            // Handle cases where the user is not authenticated
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

}