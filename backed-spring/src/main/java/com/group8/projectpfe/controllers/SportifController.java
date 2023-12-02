package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.services.SportifService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sportifs")
public class SportifController {
    private final SportifService sportifService;

    @GetMapping("")
    public ResponseEntity<List<SportifDTO>> getSportifs(){
        List<SportifDTO> athletes = sportifService.getSportifs();
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportifDTO> getSportifById(@PathVariable Long id) {
        SportifDTO sportifDTO = sportifService.getSportifById(id);
        if (sportifDTO != null) {
            return new ResponseEntity<>(sportifDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
