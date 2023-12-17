package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import com.group8.projectpfe.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/challenges")
@RequiredArgsConstructor
public class ChallengeController {


    private final ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<List<ChallengeDTO>> getAllChallenges() {
        List<ChallengeDTO> challenges = challengeService.getAllChallenges();
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDTO> getChallengeById(@PathVariable int id) {
        return challengeService.getChallengeById(id)
                .map(challenge -> new ResponseEntity<>(challenge, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ChallengeDTO> createChallenge(@RequestBody ChallengeDTO challengeDetails) {
        ChallengeDTO createdChallenge = challengeService.createChallenge(challengeDetails);
        return new ResponseEntity<>(createdChallenge, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeDTO> updateChallenge(@PathVariable int id, @RequestBody ChallengeDTO updatedChallengeDetails) {
        ChallengeDTO updatedChallenge = challengeService.updateChallenge(id, updatedChallengeDetails);
        if (updatedChallenge != null) {
            return new ResponseEntity<>(updatedChallenge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable int id) {
        challengeService.deleteChallenge(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}