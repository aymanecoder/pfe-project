package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sports")
@RequiredArgsConstructor
public class SportController {


    private final SportService sportService;

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<SportDTO>> getAllSports() {
        List<SportDTO> sports = sportService.getAllSports();
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportDTO> getSportById(@PathVariable int id) {
        return sportService.getSportById(id)
                .map(sport -> new ResponseEntity<>(sport, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SportDTO> createSport(@RequestParam("logo") MultipartFile logo, @ModelAttribute SportDTO sportDetails) {
        if (!logo.isEmpty()) {
            try {
                String imagePath = imageService.saveImage(logo); // Save the image and get the generated file path
                if (sportDetails.getLogoPath() != null) {
                    imageService.deleteProfile(sportDetails.getLogoPath()); // Delete the old profile picture
                }
                sportDetails.setLogoPath(imagePath); // Set the image path in the DTO instead of the byte array
            } catch (IOException e) {
                // Handle file processing error
                return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        SportDTO createdSport = sportService.createSport(sportDetails);
        return new ResponseEntity<>(createdSport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportDTO> updateSport(@PathVariable int id,@RequestParam("logo") MultipartFile logo, @ModelAttribute SportDTO updatedSportDetails) {
        if (!logo.isEmpty()) {
            try {
                String imagePath = imageService.saveImage(logo); // Save the image and get the generated file path
                if (updatedSportDetails.getLogoPath() != null) {
                    imageService.deleteProfile(updatedSportDetails.getLogoPath()); // Delete the old profile picture
                }
                updatedSportDetails.setLogoPath(imagePath); // Set the image path in the DTO instead of the byte array
            } catch (IOException e) {
                // Handle file processing error
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        SportDTO updatedSport = sportService.updateSport(id, updatedSportDetails);
        if (updatedSport != null) {
            return new ResponseEntity<>(updatedSport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSport(@PathVariable int id) throws IOException {
        SportDTO sportDTO = sportService.getSportById(id).orElse(null);
        if (sportDTO != null){
            imageService.deleteProfile(sportDTO.getLogoPath());
            sportService.deleteSport(id);
        }else{
            return new ResponseEntity<>("Unauthorized delete or sportif not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
