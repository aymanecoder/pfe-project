package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.JwtService;
import com.group8.projectpfe.services.SportifService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import java.util.logging.Logger;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sportifs")
public class SportifController {
    private static final Logger LOGGER = Logger.getLogger(SportifController.class.getName());
    private final SportifService sportifService;
    private final ImageService imageService;
    @GetMapping("")
    public ResponseEntity<List<SportifDTO>> getSportifs(){
        List<SportifDTO> athletes = sportifService.getSportifs();
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportifDTO> getSportifById(@PathVariable Long id) {
        SportifDTO sportifDTO = sportifService.getSportifById(id);
        if (sportifDTO != null) {
            return new ResponseEntity<>(sportifDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User user =(User) authentication.getPrincipal(); // Retrieve the username (assuming it's the email)
            SportifDTO sportifDTO = sportifService.getSportifById(Long.valueOf(id));

            if (sportifDTO != null && sportifDTO.getId().equals(user.getId())) {
                try {
                    imageService.deleteProfile(sportifDTO.getPicturePath());
                    sportifService.deleteSportif(id, user.getId());
                } catch (IOException e) {
                    return new ResponseEntity<>("Failed to delete user or profile image", HttpStatus.INTERNAL_SERVER_ERROR);
                }

                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized delete or sportif not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSportif(@PathVariable Integer id, @RequestParam("file") MultipartFile file, @ModelAttribute SportifDTO sportifDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal(); // Retrieve the authenticated user
            SportifDTO existingSportif = sportifService.getSportifById(Long.valueOf(id));

            if (existingSportif != null && existingSportif.getId().equals(user.getId())) {
                // Check if the sportif to be updated belongs to the authenticated user
                sportifDTO.setId(id); // Set the ID in the DTO
                // Process the image file
                if (!file.isEmpty()) {
                    try {
                        String imagePath = imageService.saveImage(file); // Save the image and get the generated file path
                        if (existingSportif.getPicturePath() != null) {
                            imageService.deleteProfile(existingSportif.getPicturePath()); // Delete the old profile picture
                        }
                        sportifDTO.setPicturePath(imagePath); // Set the image path in the DTO instead of the byte array
                    } catch (IOException e) {
                        // Handle file processing error
                        return new ResponseEntity<>("Failed to process the image", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                sportifService.updateSportif(sportifDTO);
                return new ResponseEntity<>("Sportif updated successfully", HttpStatus.OK);
            } else {
                // Handle unauthorized update or sportif not found
                return new ResponseEntity<>("Unauthorized update or sportif not found", HttpStatus.NOT_FOUND);
            }
        } else {
            // Handle cases where the user is not authenticated
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }


}

