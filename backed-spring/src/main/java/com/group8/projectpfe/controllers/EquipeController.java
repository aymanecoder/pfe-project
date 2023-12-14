package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.EquipeDTO;
import com.group8.projectpfe.services.EquipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class EquipeController {


    private final EquipeService equipeService;

    @GetMapping
    public ResponseEntity<List<EquipeDTO>> getAllTeams() {
        List<EquipeDTO> teams = equipeService.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> getTeamById(@PathVariable int id) {
        return equipeService.getTeamById(id)
                .map(team -> new ResponseEntity<>(team, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<EquipeDTO> createTeam(@RequestBody EquipeDTO teamDetails) {
        EquipeDTO createdTeam = equipeService.createTeam(teamDetails);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeDTO> updateTeam(@PathVariable int id, @RequestBody EquipeDTO updatedTeamDetails) {
        EquipeDTO updatedTeam = equipeService.updateTeam(id, updatedTeamDetails);
        if (updatedTeam != null) {
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) {
        equipeService.deleteTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
