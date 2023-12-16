package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.services.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sports")
@RequiredArgsConstructor
public class SportController {


    private final SportService sportService;

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
    public ResponseEntity<SportDTO> createSport(@RequestBody SportDTO sportDetails) {
        SportDTO createdSport = sportService.createSport(sportDetails);
        return new ResponseEntity<>(createdSport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportDTO> updateSport(@PathVariable int id, @RequestBody SportDTO updatedSportDetails) {
        SportDTO updatedSport = sportService.updateSport(id, updatedSportDetails);
        if (updatedSport != null) {
            return new ResponseEntity<>(updatedSport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable int id) {
        sportService.deleteSport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
