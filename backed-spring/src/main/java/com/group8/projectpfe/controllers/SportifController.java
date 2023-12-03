package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.services.JwtService;
import com.group8.projectpfe.services.SportifService;
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
@RequestMapping("api/v1/sportifs")
public class SportifController {
    private static final Logger LOGGER = Logger.getLogger(SportifController.class.getName());
    private final SportifService sportifService;
private final JwtService jwtService;
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
            sportifService.deleteSportif(id,user.getId());
        } else {
            // Handle cases where the user is not authenticated
            return new ResponseEntity<>("User not authenticated", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }


}
