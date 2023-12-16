package com.group8.projectpfe.mappers.impl;

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
    public TeamDTO mapTo(Team equipe) {
        TeamDTO teamDTO = modelMapper.map(equipe, TeamDTO.class);
        SportifDTO sportifDTO=sportifMapper.mapTo(equipe.getAdmin());
        List<User> userList = equipe.getMembers();
        List<SportifDTO> sportifDTOList = userList.stream()
                .map(sportifMapper::mapTo)
                .collect(Collectors.toList());

        teamDTO.setAdmin(sportifDTO);
        teamDTO.setMembers(sportifDTOList);
        return teamDTO;
    }

    @Override
    public Team mapFrom(TeamDTO teamDTO) {
        Team equipe = modelMapper.map(teamDTO, Team.class);

        User coach=sportifMapper.mapFrom(teamDTO.getAdmin());
        List<SportifDTO> sportifDTOList = teamDTO.getMembers();
        List<User> userList = sportifDTOList.stream()
                .map(sportifMapper::mapFrom)
                .collect(Collectors.toList());

        equipe.setAdmin(coach);
        equipe.setMembers(userList);
        return equipe;
    }
}
