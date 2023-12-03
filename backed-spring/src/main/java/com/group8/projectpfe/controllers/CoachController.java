package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.services.JwtService;
import com.group8.projectpfe.services.CoachService;
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
@RequestMapping("api/v1/coachs")
public class CoachController {
    private static final Logger LOGGER = Logger.getLogger(CoachController.class.getName());
    private final CoachService coachService;
private final JwtService jwtService;
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
            coachService.deleteCoach(id,user.getId());
        } else {
            // Handle cases where the user is not authenticated
            return new ResponseEntity<>("User not authenticated", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoach(@PathVariable Integer id, @RequestBody CoachDTO coachDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal(); // Retrieve the authenticated user
            CoachDTO existingCoach = coachService.getCoachById(Long.valueOf(id));

            if (existingCoach != null && existingCoach.getId().equals(user.getId())) {
                // Check if the coach to be updated belongs to the authenticated user
                coachDTO.setId(id); // Set the ID in the DTO
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
