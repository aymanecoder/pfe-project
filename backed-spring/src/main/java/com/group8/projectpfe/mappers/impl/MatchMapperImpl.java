package com.group8.projectpfe.mappers.impl;


import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchMapperImpl implements Mapper<Match, MatchDto> {

    private final ModelMapper modelMapper;
    private final TeamMapperImpl teamMapper;
    private final SportMapperImpl sportMapper;

    public MatchMapperImpl(ModelMapper modelMapper, TeamMapperImpl teamMapper, SportMapperImpl sportMapper) {
        this.modelMapper = modelMapper;
        this.teamMapper = teamMapper;
        this.sportMapper = sportMapper;
    }

    @Override
    public MatchDto mapTo(Match match) {
        MatchDto matchDTO = modelMapper.map(match, MatchDto.class);

        SportDTO sportDTO = sportMapper.mapTo(match.getTypeDeSport());
        List<TeamDTO> teamDTOList = match.getTeams().stream()
                .map(teamMapper::mapTo)
                .collect(Collectors.toList());

        matchDTO.setTypeDeSport(sportDTO);
        matchDTO.setTeams(teamDTOList);

        return matchDTO;
    }

    @Override
    public Match mapFrom(MatchDto matchDTO) {
        Match match = modelMapper.map(matchDTO, Match.class);

        List<Team> teamList = matchDTO.getTeams().stream()
                .map(teamMapper::mapFrom)
                .collect(Collectors.toList());

        Sport sport = sportMapper.mapFrom(matchDTO.getTypeDeSport());

        match.setTypeDeSport(sport);
        match.setTeams(teamList);

        return match;
    }
    public void updateMatchFromDto(MatchDto matchDTO, Match match) {
        modelMapper.map(matchDTO, match);
    }
}
