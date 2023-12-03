package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.services.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/programmes")
public class ProgrammeController {

    private final ProgrammeService programmeService;

    @Autowired
    public ProgrammeController(ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

    @GetMapping
    public ResponseEntity<List<ProgrammeDTO>> getAllProgrammes() {
        List<ProgrammeDTO> programmes = programmeService.getAllProgrammes();
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgrammeDTO> getProgrammeById(@PathVariable Long id) {
        Optional<ProgrammeDTO> programme = programmeService.getProgrammeById(id);
        return programme.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProgrammeDTO> createProgramme(@RequestBody ProgrammeDTO programmeDetails) {
        ProgrammeDTO createdProgramme = programmeService.createProgramme(programmeDetails);
        return new ResponseEntity<>(createdProgramme, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgrammeDTO> updateProgramme(
            @PathVariable Long id, @RequestBody ProgrammeDTO updatedProgrammeDetails) {
        ProgrammeDTO updatedProgramme = programmeService.updateProgramme(id, updatedProgrammeDetails);
        if (updatedProgramme != null) {
            return new ResponseEntity<>(updatedProgramme, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramme(@PathVariable Long id) {
        programmeService.deleteProgramme(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProgrammeDTO>> searchByTitle(@RequestParam String title) {
        List<ProgrammeDTO> programmes = programmeService.searchByTitle(title);
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }
}
