package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.EquipeDTO;
import com.group8.projectpfe.services.EquipeService;
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
@RequestMapping("api/v1/equipes")
public class EquipeController {
    private static final Logger LOGGER = Logger.getLogger(EquipeController.class.getName());
    private final EquipeService equipeService;

    @GetMapping("")
    public ResponseEntity<List<EquipeDTO>> getAllEquipes() {
        List<EquipeDTO> equipes = equipeService.getAllEquipes();
        return new ResponseEntity<>(equipes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> getEquipeById(@PathVariable Integer id) {
        EquipeDTO equipeDTO = equipeService.getEquipeById(id);
        if (equipeDTO != null) {
            return new ResponseEntity<>(equipeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<EquipeDTO> createEquipe(@RequestBody EquipeDTO equipeDTO) {
        EquipeDTO createdEquipe = equipeService.createEquipe(equipeDTO);
        return new ResponseEntity<>(createdEquipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeDTO> updateEquipe(@PathVariable Integer id, @RequestBody EquipeDTO equipeDTO) {
        EquipeDTO updatedEquipe = equipeService.updateEquipe(id, equipeDTO);
        if (updatedEquipe != null) {
            return new ResponseEntity<>(updatedEquipe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipe(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            equipeService.deleteEquipe(id);
            return new ResponseEntity<>("Equipe deleted successfully", HttpStatus.OK);
        } else {
            // Handle cases where the user is not authenticated
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }
}
