package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.EquipeDTO;

import java.util.List;

public interface EquipeService {
    EquipeDTO createEquipe(EquipeDTO Equipe);
    EquipeDTO getEquipeById(Integer id);
    EquipeDTO updateEquipe(Integer id,EquipeDTO Equipe);
    List<EquipeDTO> getAllEquipes();
    void deleteEquipe(Integer id);
}
