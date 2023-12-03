package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.Mapper;
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

    @Override
    public TeamDTO mapTo(Team team) {
        TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);
        SportifDTO sportifDTO=sportifMapper.mapTo(team.getAdmin());
        // Map List<User> to List<SportifDTO>
        List<User> userList = team.getMembers();
        List<SportifDTO> sportifDTOList = userList.stream()
                .map(sportifMapper::mapTo) // Map User to SportifDTO using UserSportifMapper
                .collect(Collectors.toList());

        teamDTO.setAdmin(sportifDTO);
        teamDTO.setMembers(sportifDTOList);
        return teamDTO;
    }

    @Override
    public Team mapFrom(TeamDTO teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);

        User coach=sportifMapper.mapFrom(teamDTO.getAdmin());
        // Map List<SportifDTO> to List<User>
        List<SportifDTO> sportifDTOList = teamDTO.getMembers();
        List<User> userList = sportifDTOList.stream()
                .map(sportifMapper::mapFrom) // Map SportifDTO to User using UserSportifMapper
                .collect(Collectors.toList());

        team.setAdmin(coach);
        team.setMembers(userList);
        return team;
    }
}
