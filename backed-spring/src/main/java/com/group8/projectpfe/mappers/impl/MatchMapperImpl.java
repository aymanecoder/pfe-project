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
        List<TeamDTO> teamDTOList = match.getTeams().stream()
                .map(teamMapper::mapTo)
                .collect(Collectors.toList());
        SportDTO sportDTO = sportMapper.mapTo(match.getSport());

        matchDto.setTeams(teamDTOList);
        matchDto.setSport(sportDTO);

        return matchDto;
    }

    @Override
    public Match mapFrom(MatchDto matchDto) {
        Match match = modelMapper.map(matchDto, Match.class);
        List<Team> teamList = matchDto.getTeams().stream()
                .map(teamMapper::mapFrom)
                .collect(Collectors.toList());
        Sport sport = sportMapper.mapFrom(matchDto.getSport());

        match.setTeams(teamList);
        match.setSport(sport);

        return match;
    }
}