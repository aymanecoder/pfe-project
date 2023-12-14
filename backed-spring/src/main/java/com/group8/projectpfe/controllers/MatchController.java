package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.MatchDTO;
import com.group8.projectpfe.services.MatchService;
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
@RequestMapping("api/v1/matchs")
public class MatchController {
    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());
    private final MatchService matchService;

    @GetMapping("")
    public ResponseEntity<List<MatchDTO>> getAllMatchs() {
        List<MatchDTO> matchs = matchService.getAllMatchs();
        return new ResponseEntity<>(matchs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Integer id) {
        MatchDTO matchDTO = matchService.getMatchById(id);
        if (matchDTO != null) {
            return new ResponseEntity<>(matchDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<MatchDTO> createMatch(@RequestBody MatchDTO matchDTO) {
        MatchDTO createdMatch = matchService.createMatch(matchDTO);
        return new ResponseEntity<>(createdMatch, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Integer id, @RequestBody MatchDTO matchDTO) {
        MatchDTO updatedMatch = matchService.updateMatch(id, matchDTO);
        if (updatedMatch != null) {
            return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatch(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            matchService.deleteMatch(id);
            return new ResponseEntity<>("Match deleted successfully", HttpStatus.OK);
        } else {
            // Handle cases where the user is not authenticated
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }
}
