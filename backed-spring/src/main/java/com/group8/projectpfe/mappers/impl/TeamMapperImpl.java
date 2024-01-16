package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.SportDTO;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.Mapper;
import com.group8.projectpfe.repositories.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamMapperImpl implements Mapper<Team, TeamDTO> {

    private final ModelMapper modelMapper;

    private final SportifMapper sportifMapper;

    private final SportMapperImpl sportMapper;
    private final TeamRepository teamRepository;

    @Override


    public TeamDTO mapTo(Team team) {
        TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);
        SportifDTO sportifDTO=sportifMapper.mapTo(team.getAdmin());
        SportDTO sportDTO=sportMapper.mapTo(team.getSport());
        List<User> userList = team.getMembers();

        List<SportifDTO> sportifDTOList = userList.stream()
                .map(sportifMapper::mapTo)
                .collect(Collectors.toList());

        teamDTO.setSport(sportDTO);
        teamDTO.setAdmin(sportifDTO);
        teamDTO.setMembers(sportifDTOList);
        return teamDTO;
    }

    @Override
    public Team mapFrom(TeamDTO teamDTO) {
        if (teamDTO == null) {
            throw new NullPointerException("Input TeamDTO cannot be null");
        }

        Team team = modelMapper.map(teamDTO, Team.class);

        // Check for null values before mapping
        if (teamDTO.getAdmin() != null) {
            User coach = sportifMapper.mapFrom(teamDTO.getAdmin());
            team.setAdmin(coach);
        }

        if (teamDTO.getSport() != null) {
            Sport sport = sportMapper.mapFrom(teamDTO.getSport());
            team.setSport(sport);
        }

        List<SportifDTO> sportifDTOList = teamDTO.getMembers();
        if (sportifDTOList != null) {
            List<User> userList = sportifDTOList.stream()
                    .map(sportifMapper::mapFrom)
                    .collect(Collectors.toList());
            team.setMembers(userList);
        }

        // You may want to log information here for debugging
        System.out.println("Mapped Team from TeamDTO: " + team);

        // Ensure that the team entity is correctly populated
        // Note: If you are getting an EntityNotFoundException, check if the team exists in the database
        // and if the associations (admin, sport, members) are correctly mapped in the database.
        if (team.getId() != null) {
            // You may want to fetch the entity from the database to ensure it exists
            Team existingTeam = teamRepository.findById(team.getId()).orElse(null);
            if (existingTeam == null) {
                throw new EntityNotFoundException("Team with ID " + team.getId() + " not found in the database");
            }
        }

        return team;
    }

}
