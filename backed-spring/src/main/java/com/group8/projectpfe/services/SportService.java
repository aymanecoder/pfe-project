package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.SportDTO;

import java.util.List;

public interface SportService {
    SportDTO createSport(SportDTO Sport);
    SportDTO getSportById(Integer id);
    SportDTO updateSport(Integer id,SportDTO Sport);
    List<SportDTO> getAllSports();
    void deleteSport(Integer id);
}
