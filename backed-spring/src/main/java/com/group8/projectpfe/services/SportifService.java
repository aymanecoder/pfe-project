package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.SportifDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface SportifService {
    @Transactional
    List<SportifDTO> getSportifs();

    @Transactional
    SportifDTO getSportifById(Long sportifId);
    void deleteSportif(Integer id,Integer userId);

    void updateSportif(SportifDTO sportifDTO);
}
