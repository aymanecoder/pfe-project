package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class EquipeController {


    private final TeamService equipeService;

    private final ImageService imageService;
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teams = equipeService.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable int id) {
        return equipeService.getTeamById(id)
                .map(team -> new ResponseEntity<>(team, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestParam("logo") MultipartFile logo, @ModelAttribute TeamDTO teamDetails) {
        if (!logo.isEmpty()) {
            try {
                String imagePath = imageService.saveImage(logo); // Save the image and get the generated file path
                if (teamDetails.getLogoPath() != null) {
                    imageService.deleteProfile(teamDetails.getLogoPath()); // Delete the old profile picture
                }
                teamDetails.setLogoPath(imagePath); // Set the image path in the DTO instead of the byte array
            } catch (IOException e) {
                // Handle file processing error
                return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        TeamDTO createdTeam = equipeService.createTeam(teamDetails);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable int id,@RequestParam("logo") MultipartFile logo, @ModelAttribute TeamDTO updatedTeamDetails) {
        if (!logo.isEmpty()) {
            try {
                String imagePath = imageService.saveImage(logo); // Save the image and get the generated file path
                if (updatedTeamDetails.getLogoPath() != null) {
                    imageService.deleteProfile(updatedTeamDetails.getLogoPath()); // Delete the old profile picture
                }
                updatedTeamDetails.setLogoPath(imagePath); // Set the image path in the DTO instead of the byte array
            } catch (IOException e) {
                // Handle file processing error
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        TeamDTO updatedTeam = equipeService.updateTeam(id, updatedTeamDetails);
        if (updatedTeam != null) {
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable int id) throws IOException {
        TeamDTO teamDTO = equipeService.getTeamById(id).orElse(null);
        if (teamDTO != null){
            imageService.deleteProfile(teamDTO.getLogoPath());
            equipeService.deleteTeam(id);
        }else{
            return new ResponseEntity<>("Unauthorized delete or sportif not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{teamId}/join")
    public ResponseEntity<Void> joinTeam(@PathVariable("teamId") int teamId) {
        teamService.joinTeam(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
