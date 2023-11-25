package com.group8.projectpfe.controllers;

import com.group8.projectpfe.entities.Equipe;
//import com.group8.projectpfe.repositories.EquipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TestController {
//    private final EquipeRepository equipeRepository;
    @GetMapping
    public String getName(){
        return "hello world";
    }
//    @GetMapping("/equipes")
//    public List<Equipe> getEquipes(){
//        return equipeRepository.findAll();
//    }
//    @PostMapping("/add")
//    public Equipe addEquipe(@RequestBody Equipe equipe){
//        return equipeRepository.save(equipe);

//    }
}
