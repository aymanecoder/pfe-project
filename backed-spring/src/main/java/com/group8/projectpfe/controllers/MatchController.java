package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.services.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {


    private final MatchService matchService;


    @GetMapping("/{matchId}")
    public ResponseEntity<MatchDto> getMatchById(@PathVariable Integer matchId) {
        MatchDto MatchDto = matchService.getMatchById(matchId);
        return ResponseEntity.ok(MatchDto);
    }

    @GetMapping
    public ResponseEntity<List<MatchDto>> getAllMatches() {
        List<MatchDto> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    @PostMapping
    public ResponseEntity<MatchDto> createMatch(@RequestBody MatchDto MatchDto) {
        MatchDto createdMatch = matchService.createMatch(MatchDto);
        return new ResponseEntity<>(createdMatch, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MatchDto> updateMatch(@PathVariable Integer id, @RequestBody MatchDto matchDto) {
        matchDto.setId(id); // Set the ID from the path variable to the DTO
        MatchDto updatedMatch = matchService.updateMatch(matchDto);
        return ResponseEntity.ok(updatedMatch);
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer matchId) {
        matchService.deleteMatch(matchId);
        return ResponseEntity.noContent().build();
    }
}