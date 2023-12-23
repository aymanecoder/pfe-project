package com.group8.projectpfe.mappers.impl;


import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.Mapper;
import com.group8.projectpfe.utilities.ModelMapperConfigurer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MatchMapperImpl implements Mapper<Match, MatchDto> {

    private final ModelMapper modelMapper;
    private final TeamMapperImpl teamMapper;
    private final SportMapperImpl sportMapper;





    @Override
    public MatchDto mapTo(Match match) {
        MatchDto matchDto = modelMapper.map(match, MatchDto.class);

        // Assuming you have methods to map Team, Sportif, and Sport entities to their DTOs
        List<TeamDTO> teamDTOList = match.getTeams().stream()
                .map(this::mapTeamToDTO)
                .collect(Collectors.toList());

        List<SportifDTO> participantDTOList = match.getParticipants().stream()
                .map(this::mapSportifToDTO)
                .collect(Collectors.toList());

        SportDTO sportDTO = mapSportToDTO(match.getSport());

        matchDto.setTeams(teamDTOList);
        matchDto.setParticipants(participantDTOList);
        matchDto.setSport(sportDTO);

        return matchDto;
    }

    public Match mapFrom(MatchDto matchDto) {
        Match match = modelMapper.map(matchDto, Match.class);

        // Assuming you have methods to map TeamDTO, SportifDTO, and SportDTO to their entities
        List<Team> teamList = matchDto.getTeams().stream()
                .map(this::mapTeamDTOToEntity)
                .collect(Collectors.toList());

        List<User> participantList = matchDto.getParticipants().stream()
                .map(this::mapSportifDTOToEntity)
                .collect(Collectors.toList());

        Sport sport = mapSportDTOToEntity(matchDto.getSport());

        match.setTeams(teamList);
        match.setParticipants(participantList);
        match.setSport(sport);

        return match;
    }
    public void updateMatchFromDto(MatchDto matchDto, Match match) {
        modelMapper.map(matchDto, match);

        // Assuming you have methods to map TeamDTO, SportifDTO, and SportDTO to their entities
        List<Team> teamList = matchDto.getTeams().stream()
                .map(this::mapTeamDTOToEntity)
                .collect(Collectors.toList());

        List<User> participantList = matchDto.getParticipants().stream()
                .map(this::mapSportifDTOToEntity)
                .collect(Collectors.toList());

        Sport sport = mapSportDTOToEntity(matchDto.getSport());

        match.setTeams(teamList);
        match.setParticipants(participantList);
        match.setSport(sport);
    }

    public TeamDTO mapTeamToDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(team.getId());
        teamDTO.setAdmin(mapSportifToDTO(team.getAdmin()));
        teamDTO.setMembers(team.getMembers().stream().map(this::mapSportifToDTO).collect(Collectors.toList()));
        // Map other fields as needed
        return teamDTO;
    }

    public SportifDTO mapSportifToDTO(User user) {
        SportifDTO sportifDTO = new SportifDTO();
        sportifDTO.setId(user.getId());
        sportifDTO.setFirstName(user.getFirstName());
        sportifDTO.setLastName(user.getLastName());
        // Map other fields as needed
        return sportifDTO;
    }

    public SportDTO mapSportToDTO(Sport sport) {
        SportDTO sportDTO = new SportDTO();
        sportDTO.setId(sport.getId());
        sportDTO.setName(sport.getName());
        sportDTO.setDescription(sport.getDescription());
        // Map other fields as needed
        return sportDTO;
    }

    // Add methods to map TeamDTO, SportifDTO, and SportDTO to their entities
    public Team mapTeamDTOToEntity(TeamDTO teamDTO) {
        if (teamDTO == null) {
            return null; // Or handle accordingly based on your logic
        }

        Team team = new Team();
        team.setId(teamDTO.getId());

        SportifDTO adminDTO = teamDTO.getAdmin();
        if (adminDTO != null) {
            team.setAdmin(mapSportifDTOToEntity(adminDTO));
        }

        List<SportifDTO> membersDTO = teamDTO.getMembers();
        if (membersDTO != null) {
            team.setMembers(membersDTO.stream().map(this::mapSportifDTOToEntity).collect(Collectors.toList()));
        }

        // Map other fields as needed
        return team;
    }

    public User mapSportifDTOToEntity(SportifDTO sportifDTO) {
        if (sportifDTO == null) {
            return null; // Or handle accordingly based on your logic
        }

        User user = new User();
        user.setId(sportifDTO.getId());
        user.setFirstName(sportifDTO.getFirstName());
        user.setLastName(sportifDTO.getLastName());
        // Map other fields as needed
        return user;
    }


    public Sport mapSportDTOToEntity(SportDTO sportDTO) {
        Sport sport = new Sport();
        sport.setId(sportDTO.getId());
        sport.setName(sportDTO.getName());
        sport.setDescription(sportDTO.getDescription());
        // Map other fields as needed
        return sport;
    }
}
