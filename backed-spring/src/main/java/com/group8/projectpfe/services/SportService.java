package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.entities.Sport;

import java.util.List;
import java.util.Optional;

public interface SportService {
    List<SportDTO> getAllSports();

    Optional<SportDTO> getSportById(int id);

    SportDTO createSport(SportDTO teamDetails);

    SportDTO updateSport(int id, SportDTO updatedSportDetails);

    void deleteSport(int id);

}
