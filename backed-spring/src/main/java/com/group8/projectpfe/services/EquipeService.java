package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.EquipeDTO;
import java.util.List;
import java.util.Optional;

public interface EquipeService {
    List<EquipeDTO> getAllTeams();

    Optional<EquipeDTO> getTeamById(int id);

    EquipeDTO createTeam(EquipeDTO teamDetails);

    EquipeDTO updateTeam(int id, EquipeDTO updatedTeamDetails);

    void deleteTeam(int id);

    List<EquipeDTO> searchByDescription(String description);


}

