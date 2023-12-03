package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamMapperImpl implements Mapper<Team, TeamDTO> {

    private final ModelMapper modelMapper;

    @Override
    public TeamDTO mapTo(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }

    @Override
    public Team mapFrom(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }
}
