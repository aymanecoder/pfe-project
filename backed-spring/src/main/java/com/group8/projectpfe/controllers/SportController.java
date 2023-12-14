package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.services.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sports")
public class SportController {
    private static final Logger LOGGER = Logger.getLogger(SportController.class.getName());
    private final SportService sportService;

    @GetMapping("")
    public ResponseEntity<List<SportDTO>> getAllSports() {
        List<SportDTO> sports = sportService.getAllSports();
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportDTO> getSportById(@PathVariable Integer id) {
        SportDTO sportDTO = sportService.getSportById(id);
        if (sportDTO != null) {
            return new ResponseEntity<>(sportDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<SportDTO> createSport(@RequestBody SportDTO sportDTO) {
        SportDTO createdSport = sportService.createSport(sportDTO);
        return new ResponseEntity<>(createdSport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportDTO> updateSport(@PathVariable Integer id, @RequestBody SportDTO sportDTO) {
        SportDTO updatedSport = sportService.updateSport(id, sportDTO);
        if (updatedSport != null) {
            return new ResponseEntity<>(updatedSport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSport(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            sportService.deleteSport(id);
            return new ResponseEntity<>("Sport deleted successfully", HttpStatus.OK);
        } else {
            // Handle cases where the user is not authenticated
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }
}
