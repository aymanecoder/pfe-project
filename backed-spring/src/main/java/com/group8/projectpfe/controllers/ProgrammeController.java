package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.ProgrammeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/programmes")
public class ProgrammeController {

    private final ProgrammeService programmeService;
    private final ImageService imageService;

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
    public ResponseEntity<ProgrammeDTO> createProgramme(@RequestParam("program") MultipartFile program,@ModelAttribute ProgrammeDTO programmeDetails) {
        if (!program.isEmpty()) {
            try {
                String imagePath = imageService.saveImage(program); // Save the image and get the generated file path
                if (programmeDetails.getPicturePath() != null) {
                    imageService.deleteProfile(programmeDetails.getPicturePath()); // Delete the old profile picture
                }
                programmeDetails.setPicturePath(imagePath); // Set the image path in the DTO instead of the byte array
            } catch (IOException e) {
                // Handle file processing error
                return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        ProgrammeDTO createdProgramme = programmeService.createProgramme(programmeDetails);
        return new ResponseEntity<>(createdProgramme, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgrammeDTO> updateProgramme(
            @PathVariable Long id,@RequestParam("program") MultipartFile program, @ModelAttribute ProgrammeDTO updatedProgrammeDetails) {
        if (!program.isEmpty()) {
            try {
                String imagePath = imageService.saveImage(program); // Save the image and get the generated file path
                if (updatedProgrammeDetails.getPicturePath() != null) {
                    imageService.deleteProfile(updatedProgrammeDetails.getPicturePath()); // Delete the old profile picture
                }
                updatedProgrammeDetails.setPicturePath(imagePath); // Set the image path in the DTO instead of the byte array
            } catch (IOException e) {
                // Handle file processing error
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        ProgrammeDTO updatedProgramme = programmeService.updateProgramme(id, updatedProgrammeDetails);
        if (updatedProgramme != null) {
            return new ResponseEntity<>(updatedProgramme, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProgramme(@PathVariable Long id) throws IOException {
        ProgrammeDTO programmeDTO = programmeService.getProgrammeById(Long.valueOf(id)).orElse(null);
        if (programmeDTO != null){
        imageService.deleteProfile(programmeDTO.getPicturePath());
        programmeService.deleteProgramme(id);
        }else{
            return new ResponseEntity<>("Unauthorized delete or sportif not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProgrammeDTO>> searchByTitle(@RequestParam String title) {
        List<ProgrammeDTO> programmes = programmeService.searchByTitle(title);
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }
    @GetMapping("/byTypeProgram/{typeProgram}")
    public ResponseEntity<List<ProgrammeDTO>> getProgramsByTypeProgram(@PathVariable String typeProgram) {
        List<ProgrammeDTO> programmes = programmeService.getProgramsByTypeProgram(typeProgram);
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

}
