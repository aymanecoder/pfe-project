package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.TeamDTO;
import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDTO> getAllTeams();

    Optional<TeamDTO> getTeamById(int id);

    TeamDTO createTeam(TeamDTO teamDetails);

    TeamDTO updateTeam(int id, TeamDTO updatedTeamDetails);

    void deleteTeam(int id);

    List<TeamDTO> searchByDescription(String description);
    public List<TeamDTO> searchByDescriptionForTest(String description);
    void joinTeam(int teamId);



}

